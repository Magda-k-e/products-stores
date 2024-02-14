package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.service.ProductCategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
