package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.ProductCategory;
import com.products.productsstoresapp.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    public void addProductCategory(ProductCategory productCategory){
        productCategoryRepository.save(productCategory);
        //saves productcategory to the database
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategoryRepository.findAll()
                .forEach(productCategories::add);
        return productCategories;
    }

    //get productcategory by id
    public Optional<ProductCategory> getProductCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }

    public List<ProductCategory> findByDescription(String description){
        List<ProductCategory> productsByDescription = productCategoryRepository.findByDescription(description);
        return productsByDescription;

    }
}
