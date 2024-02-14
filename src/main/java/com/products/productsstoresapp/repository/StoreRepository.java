package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.Store;
import com.products.productsstoresapp.model.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);

    List<Store> findByStoreCategory(StoreCategory storeCategory);

    Optional<Store> findById(Long id);
}
