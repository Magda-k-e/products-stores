package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.service.ProductCategoryService;
import com.products.productsstoresapp.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }
}

