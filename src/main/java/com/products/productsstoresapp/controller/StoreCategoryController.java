package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.StoreCategoryMapper;
import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.service.StoreCategoryService;
import com.products.productsstoresapp.transfer.resource.StoreCategoryResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    public StoreCategoryController(StoreCategoryService storeCategoryService) {
        this.storeCategoryService = storeCategoryService;
    }

    //add category controller

    @RequestMapping(method = RequestMethod.POST, value = "/storecategories")
    public ResponseEntity<Void> addStoreCategory(@RequestBody StoreCategoryResource storeCategoryResource){
        StoreCategory storeCategory = StoreCategoryMapper.INSTANCE.toDomain(storeCategoryResource);
        storeCategoryService.addStoreCategory(storeCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //get all store categories

    @GetMapping("/storecategories")
    public ResponseEntity<List<StoreCategoryResource>> getAllStoreCategories(){
        List<StoreCategory> storeCategories = storeCategoryService.getAllStoreCategories();
        if (!storeCategories.isEmpty()){
            List<StoreCategoryResource> storeCategoryResources = storeCategories.stream()
                    .map(StoreCategoryMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(storeCategoryResources,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
