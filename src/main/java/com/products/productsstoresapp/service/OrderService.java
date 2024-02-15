package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.*;
import com.products.productsstoresapp.repository.AccountRepository;
import com.products.productsstoresapp.repository.OrderItemRepository;
import com.products.productsstoresapp.repository.OrderRepository;
import com.products.productsstoresapp.repository.ProductRepository;
import com.products.productsstoresapp.transfer.resource.OrderItemResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;


//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }

//    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository) {
//        this.orderRepository = orderRepository;
//        this.accountRepository = accountRepository;
//    }
//
//    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, OrderItemRepository orderItemRepository) {
//        this.orderRepository = orderRepository;
//        this.accountRepository = accountRepository;
//        this.orderItemRepository = orderItemRepository;
//    }

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }


    // create an order for an account

    public Order createOrderWithAccount(Order order, Long accountId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            order.setAccount(account);
            return orderRepository.save(order);
        } else {
            throw  new IllegalArgumentException("account with id"+ accountId + "not found");
        }
    }

    // add order item in an existing order with an existing product

    public OrderItem createItemForOrder( OrderItem orderItem, Long orderId, Long productId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent() && optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            Product product = optionalProduct.get();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            return orderItemRepository.save(orderItem);
        } else {
            throw  new IllegalArgumentException("order with id " + orderId + " or Product with id " + productId+ " not found");
        }

    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll()
                .forEach(orders::add);
        return orders;
    }






}
