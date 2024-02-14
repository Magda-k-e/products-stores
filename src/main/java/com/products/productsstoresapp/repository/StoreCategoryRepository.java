package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    Optional<StoreCategory> findById(Long id);
}
