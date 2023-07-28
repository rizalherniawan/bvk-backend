package com.backend.bvk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bvk.dto.request.ItemRequest;
import com.backend.bvk.dto.response.BaseResponse;
import com.backend.bvk.dto.response.ItemResponse;
import com.backend.bvk.service.item.ItemService;

@RestController
@RequestMapping("/v1/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<ItemResponse>>> getItems(@RequestParam(required = false) String itemName) {
        return new ResponseEntity<BaseResponse<List<ItemResponse>>>(new BaseResponse<List<ItemResponse>>(true, null, 
            this.itemService.findItem(itemName)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<String>> addItem(@RequestBody ItemRequest payload) {
        this.itemService.registerItem(payload);
        return new ResponseEntity<BaseResponse<String>>(new BaseResponse<String>(true, null, "ok"), HttpStatus.OK);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<BaseResponse<String>> updateItem(@RequestBody ItemRequest payload, @PathVariable String itemId) {
        this.itemService.updateItem(itemId, payload);
        return new ResponseEntity<BaseResponse<String>>(new BaseResponse<String>(true, null, "ok"), HttpStatus.OK);
    }
}
