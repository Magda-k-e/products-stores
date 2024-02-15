package com.products.productsstoresapp.repository;


import com.products.productsstoresapp.model.Account;
import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByAccount(Account account);
}
