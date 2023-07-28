package com.backend.bvk.service.transaction;

import com.backend.bvk.dto.response.TransactionSummary;
import com.backend.bvk.model.transaction.Transaction;

public interface TransactionService {
    Transaction initiateTransaction();
    TransactionSummary finalizeTransaction(String transactionId);
}
