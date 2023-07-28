package com.backend.bvk.service.cart;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.bvk.dto.request.CartRequest;
import com.backend.bvk.dto.request.ItemRequest;
import com.backend.bvk.exception.NotFoundException;
import com.backend.bvk.exception.RequestException;
import com.backend.bvk.model.cart.Cart;
import com.backend.bvk.model.item.Item;
import com.backend.bvk.model.transaction.Transaction;
import com.backend.bvk.model.user.User;
import com.backend.bvk.repository.CartRepository;
import com.backend.bvk.repository.ItemRepository;
import com.backend.bvk.service.transaction.TransactionService;
import com.backend.bvk.service.user.UserService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    UserService userService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CartRepository cartRepository;

    @Override
    @Transactional
    public void addToCart(CartRequest payload) {
        if(StringUtils.isEmpty(payload.getUserRequest().getName()) || StringUtils.isEmpty(payload.getUserRequest().getEmail())) {
            throw new RequestException("name and email cannot be empty");
        }
        if(!EmailValidator.getInstance().isValid(payload.getUserRequest().getEmail())) {
            throw new RequestException("plase input valid email");
        }
        User user = this.userService.findOrCreateUser(payload.getUserRequest());
        Transaction transaction = this.transactionService.initiateTransaction();
        if(payload.getItemRequests().length == 0) {
            throw new RequestException("please input item");
        }
        for(ItemRequest item : payload.getItemRequests()) {
            Optional<Item> existingItem = this.itemRepository.findById(item.getId());
            if(existingItem.isPresent() && item.getQuantity() != null) {
                Cart newCart = new Cart();
                newCart.setItem(existingItem.get());
                newCart.setUser(user);
                newCart.setTransaction(transaction);
                newCart.setQuantity(item.getQuantity());
                newCart.setCreatedAt(new Date());
                this.cartRepository.save(newCart);
            }
        }
    }

    @Override
    @Transactional
    public void updateCart(CartRequest payload, String transactionId) {
        if(StringUtils.isEmpty(payload.getUserRequest().getName()) || StringUtils.isEmpty(payload.getUserRequest().getEmail())) {
            throw new RequestException("name and email cannot be empty");
        }
        if(!EmailValidator.getInstance().isValid(payload.getUserRequest().getEmail())) {
            throw new RequestException("plase input valid email");
        }
        Optional<Cart[]> carts = this.cartRepository.findByTransactionId(transactionId);
        List<ItemRequest> newItems = new ArrayList<>();
        if(carts.get().length < 1) {
            throw new NotFoundException("carts not found");
        }
        if(payload.getItemRequests().length > 0) {
            for(ItemRequest item : payload.getItemRequests()) {
                for(int i = 0; i < carts.get().length ; i++) {
                    // remove item from cart
                    if(item.getId().equals(carts.get()[i].getItem().getId()) && carts.get()[i].getDeletedAt() == null) {
                        if(item.getIsRemove() != null && item.getIsRemove() == true) {
                            carts.get()[i].setDeletedAt(new Date());
                            carts.get()[i].setUpdatedAt(new Date());
                            this.cartRepository.save(carts.get()[i]);
                            break;
                        } else {
                            continue;
                        }
                    }
                    if(i == carts.get().length - 1) {
                        newItems.add(item);
                    }
                }
            }
            if(newItems.size() > 0) {
                User user = this.userService.findOrCreateUser(payload.getUserRequest());
                for(ItemRequest newItem: newItems) {
                    Optional<Item> existingItem = this.itemRepository.findById(newItem.getId());
                    if(existingItem.isPresent() && newItem.getQuantity() != null) {
                        Cart newCart = new Cart();
                        newCart.setItem(existingItem.get());
                        newCart.setUser(user);
                        newCart.setTransaction(carts.get()[0].getTransaction());
                        newCart.setQuantity(newItem.getQuantity());
                        newCart.setCreatedAt(new Date());
                        this.cartRepository.save(newCart);
                    }
                }
            }
        } 
    }
    
}
