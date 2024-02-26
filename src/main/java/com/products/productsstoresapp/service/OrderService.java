package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.*;
import com.products.productsstoresapp.repository.*;
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

    private final StoreRepository storeRepository;



//    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
//        this.orderRepository = orderRepository;
//        this.accountRepository = accountRepository;
//        this.orderItemRepository = orderItemRepository;
//        this.productRepository = productRepository;
//    }

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
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



///this works, temporarily commented out
    // add order item in an existing order with an existing product

//    public OrderItem createItemForOrder( OrderItem orderItem, Long orderId, Long productId){
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//        Optional<Product> optionalProduct = productRepository.findById(productId);
//        if (optionalProduct.isPresent() && optionalOrder.isPresent()){
//            Order order = optionalOrder.get();
//            Product product = optionalProduct.get();
//            orderItem.setOrder(order);
//            orderItem.setProduct(product);
//            //transfer store value
//            order.setStore(product.getStore());
//            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
//            orderItem.setPrice(totalPrice);
//            return orderItemRepository.save(orderItem);
//        } else {
//            throw  new IllegalArgumentException("order with id " + orderId + " or Product with id " + productId+ " not found");
//        }
//
//    }
//
    //create order item only from same store
public OrderItem createItemForOrder( OrderItem orderItem, Long orderId, Long productId, Long storeId){
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    Optional<Product> optionalProduct = productRepository.findById(productId);
    Optional<Store> optionalStore = storeRepository.findById(storeId);

    if (optionalProduct.isPresent() && optionalOrder.isPresent() && optionalStore.isPresent()) {

        Order order = optionalOrder.get();
        Product product = optionalProduct.get();
        //if order contains an item, check the store id
        if (orderContainsItems(order)){
            if(product.getStore() == orderItem.getOrder().getStore()){ //
                //Store store = optionalStore.get();

                orderItem.setOrder(order);
                orderItem.setProduct(product);
                //transfer store value
                order.setStore(product.getStore());
                BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
                orderItem.setPrice(totalPrice);
                return orderItemRepository.save(orderItem);
            } else {
                throw new IllegalArgumentException("you added a product from another store");
            }
        } else {
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            //transfer store value
            order.setStore(product.getStore());
            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            orderItem.setPrice(totalPrice);
            return orderItemRepository.save(orderItem);
        }
        }
     else{
            throw new IllegalArgumentException("order with id " + orderId + " or Product with id " + productId + " not found");
        }
    }



    //check if order contains orderitems already
    //returns true if items exist, false if the list is empty
    public boolean orderContainsItems(Order order){
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        return !orderItems.isEmpty();

    }

    //check if store id given by the endpoint is identical to store id saved in the existing order item
    //true if id identical
    //false if id not identcal
//    public boolean storeIdIdentical(Long existingStoreId, Long postStoreId){
//        Optional<Store> store = storeRepository.findById(existingStoreId);
//        return existingStoreId.equals(postStoreId);
//
//    }
    public OrderItem createOrderItemWithCondition (Long productId, Long orderId, OrderItem orderItem) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        //Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);
        //Optional<Store> optionalStore = storeRepository.findById(storeId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        //OrderItem orderItem = optionalOrderItem.get();
        Product product = optionalProduct.get();
        Order order = optionalOrder.get();
        product.getStore().getId();

        //orderItem.getProduct().getStore();
        // if already an order
        if (orderContainsItems(order)) {
            order.getStore().getId();
            if (Objects.equals(product.getStore().getId(), order.getStore().getId())) {
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                //transfer store value
                order.setStore(product.getStore());
                BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
                orderItem.setPrice(totalPrice);
                return orderItemRepository.save(orderItem);
            } else {
                List<OrderItem> allItemsOfOrder = orderItemRepository.findByOrder(order);
                orderItemRepository.deleteAll(allItemsOfOrder);
                order.setStore(null);
                orderRepository.save(order);
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                //transfer store value
                order.setStore(product.getStore());
                BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
                orderItem.setPrice(totalPrice);
                return orderItemRepository.save(orderItem);
                //throw  new IllegalArgumentException("you added a product from another store, all order items will be cleared");
            }

        } else {
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            //transfer store value
            order.setStore(product.getStore());
            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            orderItem.setPrice(totalPrice);
            return orderItemRepository.save(orderItem);
        }

    }

    //delete all order items from order method
    //orderItemRepository.findByOrder(order)
//    public void deleteAllOrderItems( Order order){
//        List<OrderItem> allItemsOfOrder = orderItemRepository.findByOrder(order);
//        orderItemRepository.deleteAll(allItemsOfOrder);
//    }


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



    //sort number of orders by store showing 1 store category each time
    public Map<Store, Long> getStoresCategorySorted(List<Store> stores, StoreCategory storeCategory){
        Map<Store,Long> ordersCountByStore = stores.stream()
                .filter(store -> store.getStoreCategory().getDescription().equals(storeCategory.getDescription()))
                        .collect(Collectors.toMap(store -> store, store -> (long) orderRepository.findByStore(store).size()));
        Map<Store, Long> sortedStores = ordersCountByStore.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2)-> e2, LinkedHashMap::new));

        return sortedStores;

    }


}
