package com.products.productsstoresapp.controller;

import com.products.productsstoresapp.mapper.AccountMapper;
import com.products.productsstoresapp.mapper.ProductMapper;
import com.products.productsstoresapp.model.Account;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.service.AccountService;
import com.products.productsstoresapp.transfer.resource.AccountResource;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    //add an account
    @RequestMapping(method = RequestMethod.POST, value = "/accounts")
    public ResponseEntity<Void> addAccount(@RequestBody AccountResource accountResource){
        Account account = AccountMapper.INSTANCE.toDomain(accountResource);
        accountService.addAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //get all accounts
    @GetMapping("accounts")
    public ResponseEntity<List<AccountResource>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        if (!accounts.isEmpty()){
            List<AccountResource> accountResources = accounts.stream()
                    .map(AccountMapper.INSTANCE::toResource)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(accountResources, HttpStatus.OK);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

    // get account by id
    @GetMapping("accounts/{id}")
    public ResponseEntity<AccountResource> getAccount(@PathVariable Long id){
        Optional<Account> account = accountService.getAccount(id);
        if (account.isPresent()){
            AccountResource accountResource =AccountMapper.INSTANCE.toResource(account.get());
            return new ResponseEntity<>(accountResource,HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //delete account
    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{id}")
    public void deleteAccount(@PathVariable Long id){accountService.deleteAccount(id);}

    //search account by email
    ///searchaccounts?email=elpap@mail.com
    @GetMapping("/searchaccounts")
    public ResponseEntity<AccountResource> findAccountByEmail (@RequestParam String email){
        Optional<Account> account = accountService.getAccountByEmail(email);
        if (account.isPresent()){
            AccountResource accountResource = AccountMapper.INSTANCE.toResource(account.get());
            return new ResponseEntity<>(accountResource,HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
