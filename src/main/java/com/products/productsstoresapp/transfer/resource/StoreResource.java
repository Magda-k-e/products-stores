package com.products.productsstoresapp.transfer.resource;

import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.model.StoreCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreResource {
    private Long id;
    private String name;
    private String address;

    private List<Product> products;

    private StoreCategory storeCategory;

}
