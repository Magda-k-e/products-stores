package com.products.productsstoresapp.model;

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
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serial;

    private BigDecimal price;

    @ToString.Exclude
    @ManyToOne
    //@JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    @ManyToOne
    private Store store;

}
