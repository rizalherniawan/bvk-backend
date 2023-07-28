package com.backend.bvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bvk.dto.request.CartRequest;
import com.backend.bvk.dto.response.BaseResponse;
import com.backend.bvk.service.cart.CartService;

@RestController
@RequestMapping("/v1/cart")
public class CartController {
    
    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> addCart(@RequestBody CartRequest payload) {
        this.cartService.addToCart(payload);
        return new ResponseEntity<BaseResponse<String>>(new BaseResponse<String>(true, null, "ok"), HttpStatus.OK);
    }

    @PutMapping("{transactionId}")
    public ResponseEntity<BaseResponse<String>> updateCart(@RequestBody CartRequest payload, @PathVariable String transactionId) {
        this.cartService.updateCart(payload, transactionId);
        return new ResponseEntity<BaseResponse<String>>(new BaseResponse<String>(true, null, "ok"), HttpStatus.OK);
    }
}
