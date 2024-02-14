package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.ProductCategoryMapper;
import com.products.productsstoresapp.service.ProductCategoryService;
import com.products.productsstoresapp.transfer.resource.ProductCategoryResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.products.productsstoresapp.model.ProductCategory;

import java.util.List;

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

    //get all product categories
    @GetMapping("/productcategories")
    public ResponseEntity<List<ProductCategoryResource>> getAllProductCategories(){
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
        if (!productCategories.isEmpty()){
            List<ProductCategoryResource> productCategoryResources = productCategories.stream()
                    .map(ProductCategoryMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(productCategoryResources,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    // get category by description
    @RequestMapping("/productcategories/{description}")
    public ResponseEntity<List<ProductCategoryResource>> getProductCategory(@PathVariable String description){
        List<ProductCategory> productCategories = productCategoryService.findByDescription(description);
        if (!productCategories.isEmpty()) {
            List<ProductCategoryResource> productCategoryResources = productCategories.stream()
                    .map(ProductCategoryMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(productCategoryResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
