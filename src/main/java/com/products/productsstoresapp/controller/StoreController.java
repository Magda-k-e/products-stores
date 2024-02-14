package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.StoreMapper;
import com.products.productsstoresapp.model.Store;
import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.service.StoreCategoryService;
import com.products.productsstoresapp.service.StoreService;
import com.products.productsstoresapp.transfer.resource.StoreResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StoreController {

    private final StoreService storeService;
    private final StoreCategoryService storeCategoryService;

    public StoreController(StoreService storeService, StoreCategoryService storeCategoryService) {
        this.storeService = storeService;
        this.storeCategoryService = storeCategoryService;
    }

    //add store with category

    @PostMapping("/stores/{categoryId}/storeswithcategory")
    public ResponseEntity<Store> createStoreWithCategory(@RequestBody StoreResource storeResource, @PathVariable Long categoryId){
        Store createdStore = StoreMapper.INSTANCE.toDomain(storeResource);
        Store createdStoreWithCategory = storeService.createStoreWithCategory(createdStore,categoryId);
        return new ResponseEntity<>(createdStoreWithCategory, HttpStatus.CREATED);
    }


// get all stores using resource

    @GetMapping("stores")
    public ResponseEntity<List<StoreResource>> getAllStores(){
        List<Store> stores = storeService.getAllStores();
        if (!stores.isEmpty()){
            List<StoreResource> storeResources = stores.stream()
                    .map(StoreMapper.INSTANCE::toResource)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(storeResources,HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // search stores by name
    ///searchstores?name=goodys
    @GetMapping("searchstores")
    public ResponseEntity<StoreResource> findStoreByName(@RequestParam String name){
        Optional<Store> store = storeService.findStoreByName(name);
        if (store.isPresent()){
            StoreResource storeResource = StoreMapper.INSTANCE.toResource(store.get());
            return new ResponseEntity<>(storeResource,HttpStatus.OK);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //search for stores by store category
    @GetMapping("/storesbycategory/{categoryId}")
    public ResponseEntity<List<StoreResource>> getStoresByCategory(@PathVariable Long categoryId){
        Optional<StoreCategory> storeCategory = storeCategoryService.getStoreCategoryById(categoryId);
        if (storeCategory.isPresent()){
            List<Store> stores = storeService.getStoresByCategory(storeCategory.get());
            List<StoreResource> storeResources = stores.stream()
                    .map(StoreMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(storeResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
