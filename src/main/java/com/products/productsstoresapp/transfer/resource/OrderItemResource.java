package com.products.productsstoresapp.transfer.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.model.OrderItem;
import com.products.productsstoresapp.model.Product;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderItemResource {
    private  Long id;


    private Product product;

    private Order order;

    private Integer quantity;

    private BigDecimal price;
}

