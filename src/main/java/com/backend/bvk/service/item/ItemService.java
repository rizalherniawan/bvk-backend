package com.backend.bvk.service.item;

import java.util.List;

import com.backend.bvk.dto.request.ItemRequest;
import com.backend.bvk.dto.response.ItemResponse;


public interface ItemService {
    void registerItem(ItemRequest payload);
    void updateItem(String itemId, ItemRequest payload);
    List<ItemResponse> findItem(String itemName);
}
