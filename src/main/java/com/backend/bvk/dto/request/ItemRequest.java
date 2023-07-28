package com.backend.bvk.dto.request;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    String id;
    String name;
    Integer price;
    Integer quantity;
    Boolean isRemove;
}
