package com.backend.bvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bvk.dto.response.BaseResponse;
import com.backend.bvk.dto.response.TransactionSummary;
import com.backend.bvk.service.transaction.TransactionService;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;

    @PutMapping("{transactionId}")
    public ResponseEntity<BaseResponse<TransactionSummary>> finalizeTransaction(@PathVariable String transactionId) {
        return new ResponseEntity<BaseResponse<TransactionSummary>>(new BaseResponse<>(true, null, 
            this.transactionService.finalizeTransaction(transactionId)), HttpStatus.OK);
    }
}
