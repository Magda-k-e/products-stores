package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.ProductCategory;
import com.products.productsstoresapp.model.Store;
import com.products.productsstoresapp.repository.ProductCategoryRepository;
import com.products.productsstoresapp.repository.ProductRepository;
import com.products.productsstoresapp.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final StoreRepository storeRepository;



    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.storeRepository = storeRepository;
    }

    public Product createProductWithCategory(Product product, Long categoryId) {
        Optional<ProductCategory> categoryOptional = productCategoryRepository.findById(categoryId);
        // if the category exists
        if(categoryOptional.isPresent()) {
            ProductCategory category = categoryOptional.get();
            product.setProductCategory(category);
            return productRepository.save(product);
        } else {
            // if the category does not exist
            throw new IllegalArgumentException("Category with ID " + categoryId + " not found.");
        }
    }

    //create product for a store with category
    public Product createProductWithStoreAndCategory(Product product, Long storeId, Long categoryId) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(categoryId);

        if (optionalStore.isPresent() && optionalProductCategory.isPresent()) {
            Store store = optionalStore.get();
            ProductCategory productCategory = optionalProductCategory.get();
            product.setStore(store);
            product.setProductCategory(productCategory);
            return productRepository.save(product);
        } else {

            throw new IllegalArgumentException("Store with ID " + storeId + " or Product category with id "+ categoryId + " not found.");
        }
    }

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll()
                .forEach(products::add);
        return products;
    }

    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    //  get a product by name
    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    //get a product by product category id
    public List<Product> getProductsByCategory(ProductCategory productCategory) {
        return productRepository.findByProductCategory(productCategory);
    }

    //get a product by product category name
    public List<Product> findProductsByCategoryName(String description){
        return productRepository.findByProductCategoryDescription(description);
    }

    // get product by store
    public List<Product> getProductsByStore(Store store) {
        return productRepository.findByStore(store);
    }

}
