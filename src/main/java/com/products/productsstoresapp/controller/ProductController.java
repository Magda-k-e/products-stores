package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.ProductMapper;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.ProductCategory;
import com.products.productsstoresapp.service.ProductCategoryService;
import com.products.productsstoresapp.service.ProductService;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    // add product with category
    @PostMapping("/products/{categoryId}/productswithcategory")
    public ResponseEntity<Product> createProductWithCategory(@RequestBody ProductResource productResource, @PathVariable Long categoryId){
        Product createdProduct = ProductMapper.INSTANCE.toDomain(productResource);
        Product createdProductWithCategory = productService.createProductWithCategory(createdProduct, categoryId);
        return new ResponseEntity<>(createdProductWithCategory, HttpStatus.CREATED);
    }


    //get all products
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

    // get product by id
    @GetMapping("products/{id}")
    public ResponseEntity<ProductResource> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            ProductResource productResource = ProductMapper.INSTANCE.toResource(product.get());
            return new ResponseEntity<>(productResource, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();

        }
    }

//    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
//    public void deleteProduct(@PathVariable Long id){
//        productService.deleteProduct(id);
//    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //search product by name using Resource
    ///searchproducts?name=frappe
    @GetMapping("/searchproducts")
    public ResponseEntity<ProductResource> findProductByName(@RequestParam String name){
        Optional<Product> product = productService.findProductByName(name);
        if (product.isPresent()){
            ProductResource productResource = ProductMapper.INSTANCE.toResource(product.get());
            return new ResponseEntity<>(productResource,HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //search for products by product category
    @GetMapping("/productsbycategory/{categoryId}")
    public ResponseEntity<List<ProductResource>> getProductsByCategory(@PathVariable Long categoryId){
        Optional<ProductCategory> productCategory = productCategoryService.getProductCategoryById(categoryId);
        if (productCategory.isPresent()){
            List<Product> products = productService.getProductsByCategory(productCategory.get());
            List<ProductResource> productResources = products.stream()
                    .map(ProductMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(productResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    //  search for products by product category
    //  /productsbycategory/coffee
    @GetMapping("/productsbycategorydescription/{description}")
    public ResponseEntity<List<ProductResource>> getProductsByCategoryName(@PathVariable String description) {
        List<Product> productCategories = productService.findProductsByCategoryName(description);
        if (!productCategories.isEmpty()) {
            List<ProductResource> productResources = productCategories.stream()
                    .map(ProductMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(productResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

