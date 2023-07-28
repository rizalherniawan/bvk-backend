package com.backend.bvk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bvk.model.item.Item;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findByNameContainingIgnoreCase(String itemName);
    
}
