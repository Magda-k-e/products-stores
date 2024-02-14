package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.ProductCategoryMapper;
import com.products.productsstoresapp.service.ProductCategoryService;
import com.products.productsstoresapp.transfer.resource.ProductCategoryResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.products.productsstoresapp.model.ProductCategory;

@RestController
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // add a product category
    @RequestMapping(method = RequestMethod.POST,value = "/productcategories")
    public ResponseEntity<Void> addProductCategory(@RequestBody ProductCategoryResource productCategoryResource){
        ProductCategory productCategory = ProductCategoryMapper.INSTANCE.toDomain(productCategoryResource);
        productCategoryService.addProductCategory(productCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
