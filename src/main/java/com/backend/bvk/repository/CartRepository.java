package com.backend.bvk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bvk.model.cart.Cart;

public interface CartRepository extends JpaRepository<Cart, String>{

    Optional<Cart[]> findByTransactionId(String transactionId);

    Optional<Cart[]> findByTransactionIdAndDeletedAtIsNull(String transactionId);
    
}
