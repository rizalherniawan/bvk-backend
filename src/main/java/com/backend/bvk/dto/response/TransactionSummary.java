package com.backend.bvk.dto.response;

import java.util.List;

import com.backend.bvk.dto.request.UserRequest;

import lombok.Data;

@Data
public class TransactionSummary {
    String transactionId;
    UserRequest userInfo;
    List<ItemResponse> itemInfo;
    Integer total;
}
