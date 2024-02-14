package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.Store;
import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.repository.StoreCategoryRepository;
import com.products.productsstoresapp.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    private final StoreCategoryRepository storeCategoryRepository;

    public StoreService(StoreRepository storeRepository, StoreCategoryRepository storeCategoryRepository) {
        this.storeRepository = storeRepository;
        this.storeCategoryRepository = storeCategoryRepository;
    }



    public Store createStoreWithCategory(Store store, Long categoryId) {
        Optional<StoreCategory> categoryOptional = storeCategoryRepository.findById(categoryId);
        // if the category exists
        if(categoryOptional.isPresent()) {
            StoreCategory category = categoryOptional.get();
            store.setStoreCategory(category);
            return storeRepository.save(store);
        } else {
            // if the category does not exist
            throw new IllegalArgumentException("Category with ID " + categoryId + " not found.");
        }
    }

    public List<Store> getAllStores(){
        List<Store> stores = new ArrayList<>();
        storeRepository.findAll()
                .forEach(stores::add);
        return stores;
    }

    //search store by name

    public Optional<Store> findStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    // get stores by store category

    public List<Store> getStoresByCategory(StoreCategory storeCategory) {
        return storeRepository.findByStoreCategory(storeCategory);
    }

    public Optional<Store> getStore(Long id){
        return storeRepository.findById(id);
    }

}
