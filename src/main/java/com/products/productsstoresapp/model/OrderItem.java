package com.products.productsstoresapp.model;

import com.products.productsstoresapp.mapper.ProductMapper;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long id;

    @ManyToOne
    private Product product;
    // many OrderItem instances can be associated with a single Product.vmultiple order items can refer to the same product.

    //@ToString.Exclude
    @ManyToOne
    private Order order;

    private Integer quantity;

    private BigDecimal price;
}
