package com.products.productsstoresapp.transfer.resource;

import com.products.productsstoresapp.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductCategoryResource implements Serializable {
    private Long id;
    private String description;

    private List<Product> products;

}
