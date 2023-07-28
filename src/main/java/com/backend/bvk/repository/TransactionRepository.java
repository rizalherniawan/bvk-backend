package com.backend.bvk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.bvk.model.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query(value = "SELECT SUM(items.price * carts.quantity) FROM items INNER JOIN carts ON carts.item_id = items.id " + 
        "WHERE carts.deleted_at IS NULL AND carts.transaction_id = :transactionId", nativeQuery = true)
    Integer countTotalTransaction(String transactionId);   
}
