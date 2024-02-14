package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByDescription(String description);
    Optional<ProductCategory> findById(Long id);
}
