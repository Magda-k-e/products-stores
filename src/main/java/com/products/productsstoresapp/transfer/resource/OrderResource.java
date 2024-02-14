package com.products.productsstoresapp.transfer.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.products.productsstoresapp.model.OrderItem;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResource {
    private Long id;

    private List<OrderItem> orderItems;

    private BigDecimal cost;
}
