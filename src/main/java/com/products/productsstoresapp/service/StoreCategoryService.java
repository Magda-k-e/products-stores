package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.repository.StoreCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreCategoryService {
 private final StoreCategoryRepository storeCategoryRepository;

    public StoreCategoryService(StoreCategoryRepository storeCategoryRepository) {
        this.storeCategoryRepository = storeCategoryRepository;
    }

    public void addStoreCategory(StoreCategory storeCategory){
        storeCategoryRepository.save(storeCategory);

    }

    public List<StoreCategory> getAllStoreCategories() {
        List<StoreCategory> storeCategories = new ArrayList<>();
        storeCategoryRepository.findAll()
                .forEach(storeCategories::add);
        return storeCategories;
    }

    //get store category by id
    public Optional<StoreCategory> getStoreCategoryById(Long id) {
        return storeCategoryRepository.findById(id);
    }
}
