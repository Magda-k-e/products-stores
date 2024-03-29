package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.OrderItemMapper;
import com.products.productsstoresapp.mapper.OrderMapper;
import com.products.productsstoresapp.mapper.ProductMapper;
import com.products.productsstoresapp.model.*;
import com.products.productsstoresapp.service.*;
import com.products.productsstoresapp.transfer.resource.OrderResource;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;

    private final StoreService storeService;
    private  final StoreCategoryService storeCategoryService;

//    public OrderController(OrderService orderService, AccountService accountService) {
//        this.orderService = orderService;
//        this.accountService = accountService;
//    }

//    public OrderController(OrderService orderService, AccountService accountService, StoreService storeService) {
//        this.orderService = orderService;
//        this.accountService = accountService;
//        this.storeService = storeService;
//
//    }


    public OrderController(OrderService orderService, AccountService accountService, StoreService storeService, StoreCategoryService storeCategoryService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.storeService = storeService;
        this.storeCategoryService = storeCategoryService;
    }

    //post order with account
    //createorder?accountId=1
    @PostMapping("/createorder")
    public ResponseEntity<Order> createOrderWithAccount(@RequestBody Order order,
                                                        @RequestParam Long accountId
                                                        ) {
        Order createdOrderWithAccount = orderService.createOrderWithAccount(order, accountId);
        return new ResponseEntity<>(createdOrderWithAccount, HttpStatus.CREATED);
    }





    // this works but commented out temporarily
    //create order item for an order with a product
    //createorderitem?orderId=1&productId=1
//    @PostMapping("createorderitem")
//    public ResponseEntity<OrderItem> createOrderItemForOrder(@RequestBody OrderItem orderItem, @RequestParam Long orderId,
//                                                                   @RequestParam Long productId){
//        //OrderItem createdOrderItem = OrderItemMapper.INSTANCE.toDomain(orderItemResource);
//        OrderItem createdOrderItem = orderService.createItemForOrder(orderItem, orderId, productId);
//        return new ResponseEntity<>(createdOrderItem,HttpStatus.CREATED);
//    }

    //add item taking into account store

    @PostMapping("createorderitemstore")
    public ResponseEntity<OrderItem> createOrderItemForOrder(@RequestBody OrderItem orderItem, @RequestParam Long orderId,
                                                                   @RequestParam Long productId, @RequestParam Long storeId){
        //OrderItem createdOrderItem = OrderItemMapper.INSTANCE.toDomain(orderItemResource);
        OrderItem createdOrderItem = orderService.createItemForOrder(orderItem, orderId, productId, storeId);
        return new ResponseEntity<>(createdOrderItem,HttpStatus.CREATED);
    }

    // create order item with store condition
    //createwithcondition?productId=1&orderId=2
    @PostMapping("createwithcondition")
    public ResponseEntity<OrderItem> createOrderItemWithCondition(@RequestParam Long productId,@RequestParam Long orderId,
                                                                  @RequestBody OrderItem orderItem
                                                                  ){
        try {
            OrderItem createdOrderItem = orderService.createOrderItemWithCondition(productId, orderId, orderItem);
            return new ResponseEntity<>(createdOrderItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // view updated order, total cost is shown here

    @PutMapping("/orders/{orderId}/vieworder")
    public ResponseEntity<Order> updateOrderTotalPrice(@PathVariable Long orderId) {
        try {
            Order updatedOrder = orderService.updateOrder(orderId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //get all products
    @GetMapping("orders")
    public ResponseEntity<List<OrderResource>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (!orders.isEmpty()) {
            List<OrderResource> orderResources = orders.stream()
                    .map(OrderMapper.INSTANCE::toResource)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(orderResources, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //get orders by account
    ////ordersbyaccount/2

    @GetMapping("/ordersbyaccount/{accountId}")
    public ResponseEntity<List<OrderResource>> getOrdersByAccount(@PathVariable Long accountId){
        //Optional<Store> store = Optional.ofNullable(storeService.get(storeId));
        Optional<Account> account = accountService.getAccount(accountId);
        if (account.isPresent()){
            List<Order> orders =orderService.getOrdersByAccount(account.get());
            List<OrderResource> orderResources = orders.stream()
                    .map(OrderMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(orderResources, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //get orders by store
    // ordersbystore/1

    @GetMapping("/ordersbystore/{storeId}")
    public ResponseEntity<List<OrderResource>> getOrdersByStore(@PathVariable Long storeId){
        Optional<Store> store = storeService.getStore(storeId);
        if (store.isPresent()){
            List<Order> orders = orderService.getOrdersByStore(store.get());
            List<OrderResource> orderResources = orders.stream()
                    .map(OrderMapper.INSTANCE::toResource)
                    .toList();
            return new ResponseEntity<>(orderResources, HttpStatus.OK);
        }else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //view number of orders for each store
    @GetMapping("/viewnumberoforders/{storeId}")
    public ResponseEntity<Long> viewNumberOrders(@PathVariable Long storeId){
        Optional<Store> store = storeService.getStore(storeId);
        if (store.isPresent()){
            Long orderLong = orderService.countOrdersByStore(store.get());
            return new ResponseEntity<>(orderLong,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //get a List of the most famous stores (according to number of orders)
    @GetMapping("/viewfamousstores")
    public ResponseEntity<Map<Store,Long>> viewFamousStores(){
        List<Store> allStores =storeService.getAllStores();
        Map<Store,Long> storesSorted = orderService.getStoresSorted(allStores);
        return new ResponseEntity<>(storesSorted,HttpStatus.OK);

    }

    //get a List of the most famous stores by StoreCategory (according to number of orders)
    @GetMapping("/viewfamousstores/bystorecategory/{categoryDescription}")
    public ResponseEntity<Map<Store,Long>> getFamousStoresByCategory(@PathVariable String categoryDescription){
        List<Store> storesByCategory = storeService.findStoresByCategoryName(categoryDescription);
        Map<Store,Long> storesSorted = orderService.getStoresSorted(storesByCategory);
        return new ResponseEntity<>(storesSorted,HttpStatus.OK);

    }

}
