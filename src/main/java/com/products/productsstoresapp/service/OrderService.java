package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.*;
import com.products.productsstoresapp.repository.AccountRepository;
import com.products.productsstoresapp.repository.OrderItemRepository;
import com.products.productsstoresapp.repository.OrderRepository;
import com.products.productsstoresapp.repository.ProductRepository;
import com.products.productsstoresapp.transfer.resource.OrderItemResource;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;



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


    //update the order with the total cost

    public Order updateOrder(Long orderId){
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()){
            Order order = orderOptional.get();
            BigDecimal total = order.getOrderItems().stream()
                    .map(OrderItem::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setCost(total);
            Order updatedOrder = orderRepository.save(order);
            return updatedOrder;
        } else {
            throw new IllegalArgumentException("order not found");
        }
    }

//    public Order updateOrder(Long orderId, Long orderItemId, Order updatedOrder){
//        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);
//        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
//        List<OrderItem> orderItems = orderItemRepository.findAll();
//        if (existingOrderOptional.isPresent() && orderItemOptional.isPresent()){
//            Order existingOrder = existingOrderOptional.get();
//            OrderItem orderItem = orderItemOptional.get();
//
//
//            //existingOrder.setCost(orderItem.getPrice());
//        }
//
//
//    }


    // add order item in an existing order with an existing product

    public OrderItem createItemForOrder( OrderItem orderItem, Long orderId, Long productId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent() && optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            Product product = optionalProduct.get();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            //transfer store value
            order.setStore(product.getStore());
            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            orderItem.setPrice(totalPrice);
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


    // get orders by account

    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }


    //get orders for each store

    public List<Order> getOrdersByStore(Store store){return orderRepository.findByStore(store);}


    // count orders for each store
    public long countOrdersByStore(Store store){

        List<Order> ordersByStore = orderRepository.findByStore(store);
        long totalOrders = ordersByStore.stream().count();
        return totalOrders;

    }

    //sort number of orders by store
    public Map<Store, Long> getStoresSorted(List<Store> stores){
        Map<Store,Long> ordersCountByStore = stores.stream()
                .collect(Collectors.toMap(store -> store, store -> orderRepository.findByStore(store).stream().count()));
        Map<Store, Long> sortedStores = ordersCountByStore.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2)-> e2, LinkedHashMap::new));

        return sortedStores;

    }




}
