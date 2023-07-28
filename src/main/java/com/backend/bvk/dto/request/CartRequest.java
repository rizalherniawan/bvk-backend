package com.backend.bvk.dto.request;

import lombok.Data;

@Data
public class CartRequest {
    UserRequest userRequest;
    ItemRequest[] itemRequests;
}
