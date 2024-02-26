package com.products.productsstoresapp.repository;

import com.products.productsstoresapp.model.Account;
import com.products.productsstoresapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByEmail(String name);
}
