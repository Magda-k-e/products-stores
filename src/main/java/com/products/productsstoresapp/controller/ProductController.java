package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.ProductMapper;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.service.ProductCategoryService;
import com.products.productsstoresapp.service.ProductService;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    // add product with category using Resource
    @PostMapping("/products/{categoryId}/productswithcategory")
    public ResponseEntity<Product> createProductWithCategory(@RequestBody ProductResource productResource, @PathVariable Long categoryId){
        Product createdProduct = ProductMapper.INSTANCE.toDomain(productResource);
        Product createdProductWithCategory = productService.createProductWithCategory(createdProduct, categoryId);
        return new ResponseEntity<>(createdProductWithCategory, HttpStatus.CREATED);
    }


    //get all products using resource
    @GetMapping("products")
    public ResponseEntity<List<ProductResource>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            List<ProductResource> productsResources = products.stream()
                    .map(ProductMapper.INSTANCE::toResource)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productsResources, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

