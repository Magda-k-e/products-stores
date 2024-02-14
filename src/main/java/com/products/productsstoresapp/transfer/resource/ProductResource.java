package com.products.productsstoresapp.transfer.resource;

import com.products.productsstoresapp.model.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductResource implements Serializable {
    private Long id;
    private String name;
    private String serial;

    private BigDecimal price;


    private ProductCategory productCategory;

    //private Store store;
}
