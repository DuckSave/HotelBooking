package com.hotelbooking.hotelbooking.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Repository.AccountRepo;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    public Account getAccountById(String accountId) {
        Account account = accountRepo.findById(accountId).get();
        return account == null ? null : account;
    }

    public void updateAccount(Account account){
        accountRepo.save(account);
    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Account account = accountRepo.findAccountByPhoneNumber(phoneNumber);

        if (account == null) {
            throw new UsernameNotFoundException("Account not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (account.isRole()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }

        return new User(account.getPhoneNumber(), account.getPassword(), authorities);
    }
}
