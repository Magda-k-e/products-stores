package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.repository.StoreCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
