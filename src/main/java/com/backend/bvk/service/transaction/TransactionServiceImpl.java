package com.backend.bvk.service.transaction;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.bvk.dto.request.UserRequest;
import com.backend.bvk.dto.response.ItemResponse;
import com.backend.bvk.dto.response.TransactionSummary;
import com.backend.bvk.exception.NotFoundException;
import com.backend.bvk.exception.RequestException;
import com.backend.bvk.model.cart.Cart;
import com.backend.bvk.model.transaction.Transaction;
import com.backend.bvk.model.transaction.TransactionStatus;
import com.backend.bvk.model.user.User;
import com.backend.bvk.repository.CartRepository;
import com.backend.bvk.repository.ItemRepository;
import com.backend.bvk.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Transaction initiateTransaction() {
        Transaction newTransaction = new Transaction();
        newTransaction.setStatus(TransactionStatus.NEW);
        newTransaction.setCreatedAt(new Date());
        return this.transactionRepository.save(newTransaction);
    }

    @Override
    @Transactional
    public TransactionSummary finalizeTransaction(String transactionId) {
        TransactionSummary summary = new TransactionSummary();
        Optional<Cart[]> carts = this.cartRepository.findByTransactionIdAndDeletedAtIsNull(transactionId);
        List<ItemResponse> itemInfos = new ArrayList<>();
        if(carts.get().length == 0){
            throw new NotFoundException("transaction not found");
        }
        Transaction transaction = carts.get()[0].getTransaction();
        if(transaction.getStatus().equals(TransactionStatus.PAID)) {
            throw new RequestException("transaction already paid");
        }
        for(Cart cart : carts.get()) {
            // validate item quantity
            if(cart.getQuantity() > cart.getItem().getQuantity()) {
                throw new RequestException("item quantity is not enough");
            } else {
                // update item quantity
                Integer updatedQuantity = cart.getItem().getQuantity() - cart.getQuantity();
                cart.getItem().setQuantity(updatedQuantity);
                this.itemRepository.save(cart.getItem());
                ItemResponse item = new ItemResponse();
                item.setId(cart.getItem().getId());
                item.setName(cart.getItem().getName());
                item.setQuantity(cart.getQuantity());
                item.setPrice(cart.getItem().getPrice());
                itemInfos.add(item);
            }
        }
        // count total transaction price
        Integer totalTransaction = this.transactionRepository.countTotalTransaction(transactionId);
        transaction.setTotalTransaction(totalTransaction);
        transaction.setStatus(TransactionStatus.PAID);
        transaction.setUpdatedAt(new Date());
        this.transactionRepository.save(transaction);
        summary.setTransactionId(transactionId);
        summary.setTotal(totalTransaction);
        User user = carts.get()[0].getUser();
        UserRequest userInfo = new UserRequest();
        userInfo.setName(user.getName());
        userInfo.setEmail(user.getEmail());
        summary.setUserInfo(userInfo);
        summary.setItemInfo(itemInfos);
        return summary;
    }
    
}
