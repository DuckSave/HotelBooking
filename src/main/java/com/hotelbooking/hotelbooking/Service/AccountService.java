package com.hotelbooking.hotelbooking.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Repository.AccountRepo;

@Service
public class AccountService  {
    @Autowired
    private AccountRepo accountRepo;

    public Account getAccountById(String accountId) {
        Account account = accountRepo.findById(accountId).get();
        return account == null ? null : account;
    }

    public void updateAccount(Account account){
        accountRepo.save(account);
    }


    
    }


