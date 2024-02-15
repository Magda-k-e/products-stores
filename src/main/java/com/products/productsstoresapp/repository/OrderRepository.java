package com.products.productsstoresapp.repository;


import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
