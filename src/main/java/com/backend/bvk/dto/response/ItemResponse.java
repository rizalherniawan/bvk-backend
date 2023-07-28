package com.backend.bvk.dto.response;

import lombok.Data;

@Data
public class ItemResponse {
    String id;
    String name;
    Integer price;
    Integer quantity;
}
