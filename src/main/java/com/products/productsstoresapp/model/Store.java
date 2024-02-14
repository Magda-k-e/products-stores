package com.products.productsstoresapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    //@ManyToMany
    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Product> products;

    @ManyToOne
    private StoreCategory storeCategory;
}
