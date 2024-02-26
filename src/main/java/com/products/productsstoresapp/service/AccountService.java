package com.products.productsstoresapp.service;

import com.products.productsstoresapp.model.Account;
import com.products.productsstoresapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //add new account
    public void addAccount(Account account){
        accountRepository.save(account);
    }

    // get all accounts
    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll()
                .forEach(accounts::add);
        return accounts;
    }

    //get account by id
    public Optional<Account> getAccount(Long id){return accountRepository.findById(id);}

    //delete account
    public void deleteAccount(Long id){accountRepository.deleteById(id);}

    //find by email
    public Optional<Account> getAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }


}
