package com.products.productsstoresapp;

import com.products.productsstoresapp.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsStoresAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsStoresAppApplication.class, args);

//		var account = new Account();
//		account.setEmail("some@gmailx.com");

		var accountViaBuilder = Account.builder().lastName("Someone").firstName("Me").build();
		System.out.println(accountViaBuilder);
	}

}
