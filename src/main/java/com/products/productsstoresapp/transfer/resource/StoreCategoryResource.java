package com.products.productsstoresapp.transfer.resource;

import com.products.productsstoresapp.model.Store;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreCategoryResource {
    private Long id;
    private String description;


    private List<Store> stores;
}
