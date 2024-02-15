package com.products.productsstoresapp.transfer.resource;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResource {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;
}
