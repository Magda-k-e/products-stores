package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

     List<OrderItem> findByOrder(Order order);
}
