package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.*;

import com.example.entity.Account;
import com.example.exception.CustomException_usernameExists;
import com.example.repository.AccountRepository;

@Component
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> addAccount(Account account){
        String username = account.getUsername();
        List<String> usernames = getAllUsernames();

        if(usernames.contains(username)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(account);
        }

        if(username.length() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(accountRepository.save(account));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account);
    }

    private List<String> getAllUsernames(){
        List<Account> accountList = accountRepository.findAll();
        List<String> userNames = new ArrayList<>();
        for(Account account: accountList){
            userNames.add(account.getUsername());
        }
        return userNames;
    }
}
