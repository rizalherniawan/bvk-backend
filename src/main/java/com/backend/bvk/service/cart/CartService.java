package com.backend.bvk.service.cart;

import com.backend.bvk.dto.request.CartRequest;

public interface CartService {
    void addToCart(CartRequest payload);
    void updateCart(CartRequest payload, String transactionId);
}
