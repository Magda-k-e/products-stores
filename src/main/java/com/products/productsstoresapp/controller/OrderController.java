package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.OrderItemMapper;
import com.products.productsstoresapp.mapper.OrderMapper;
import com.products.productsstoresapp.mapper.ProductMapper;
import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.model.OrderItem;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.service.AccountService;
import com.products.productsstoresapp.service.OrderService;
import com.products.productsstoresapp.service.ProductService;
import com.products.productsstoresapp.transfer.resource.OrderResource;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;

    public OrderController(OrderService orderService, AccountService accountService) {
        this.orderService = orderService;
        this.accountService = accountService;
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

//    //post product with product category and store
//    //create?storeId=1&categoryId=1
//    @PostMapping("/products/create")
//    public ResponseEntity<Product> createProductWithStoreAndCategory(@RequestBody ProductResource productResource,
//                                                                     @RequestParam Long storeId,
//                                                                     @RequestParam Long categoryId) {
//        Product createdProduct = ProductMapper.INSTANCE.toDomain(productResource);
//        Product createdProductWithStoreAndCategory = productService.createProductWithStoreAndCategory(createdProduct, storeId, categoryId);
//        return new ResponseEntity<>(createdProductWithStoreAndCategory, HttpStatus.CREATED);
//    }

    //create order item for an order with a product
    //createorderitem?orderId=1&productId=1
    @PostMapping("createorderitem")
    public ResponseEntity<OrderItem> createOrderItemForOrder(@RequestBody OrderItem orderItem, @RequestParam Long orderId,
                                                                   @RequestParam Long productId){
        //OrderItem createdOrderItem = OrderItemMapper.INSTANCE.toDomain(orderItemResource);
        OrderItem createdOrderItem = orderService.createItemForOrder(orderItem, orderId, productId);
        return new ResponseEntity<>(createdOrderItem,HttpStatus.CREATED);
    }

    // update

    @PutMapping("/orders/{orderId}/updatedorder")
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

}
