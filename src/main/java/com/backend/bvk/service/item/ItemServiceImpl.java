package com.backend.bvk.service.item;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.bvk.dto.request.ItemRequest;
import com.backend.bvk.dto.response.ItemResponse;
import com.backend.bvk.exception.NotFoundException;
import com.backend.bvk.exception.RequestException;
import com.backend.bvk.model.item.Item;
import com.backend.bvk.repository.ItemRepository;



@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void registerItem(ItemRequest payload) {
        if(StringUtils.isEmpty(payload.getName()) || 
            payload.getPrice() == null || payload.getQuantity() == null){
            throw new RequestException("all data must be input");
        } 
        Item item = new Item();
        item.setName(payload.getName());
        item.setPrice(payload.getPrice());
        item.setQuantity(payload.getQuantity());
        item.setCreatedAt(new Date());
        this.itemRepository.save(item);
    }

    @Override
    public void updateItem(String itemId, ItemRequest payload) {
        Optional<Item> existingItem = this.itemRepository.findById(itemId);
        if(!existingItem.isPresent()) {
            throw new NotFoundException("item not found");
        }
        if(payload.getName() != null) {
            existingItem.get().setName(payload.getName());
        }
        if(payload.getPrice() != null) {
            existingItem.get().setPrice(payload.getPrice());
        }
        if(payload.getQuantity() != null) {
            existingItem.get().setQuantity(payload.getQuantity());
        }
        if(payload.getName() != null || 
            payload.getPrice() != null || payload.getQuantity() != null) {
                existingItem.get().setUpdatedAt(new Date());
                this.itemRepository.save(existingItem.get());
        }
    }

    @Override
    public List<ItemResponse> findItem(String itemName) {
        List<Item> items;
        if(itemName != null && StringUtils.isNotEmpty(itemName)) {
            items = this.itemRepository.findByNameContainingIgnoreCase(itemName);
        } else {
            items = this.itemRepository.findAll();
        }
        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        for(Item item: items) {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setName(item.getName());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setQuantity(item.getQuantity());
            itemResponses.add(itemResponse);
        }
        return itemResponses;
    }
}
