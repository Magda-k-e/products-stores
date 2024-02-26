package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.ProductCategory;
import com.products.productsstoresapp.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByProductCategory(ProductCategory productCategory);

    List<Product> findByProductCategoryDescription(String description);

     List<Product> findByStore(Store store);

}
