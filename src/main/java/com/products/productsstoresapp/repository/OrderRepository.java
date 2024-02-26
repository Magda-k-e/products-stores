package com.products.productsstoresapp.repository;


import com.products.productsstoresapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

     List<Order> findByAccount(Account account);

     List<Order> findByStore(Store store);

     //List<Order> findByStoreCategory(StoreCategory storeCategory);


}
