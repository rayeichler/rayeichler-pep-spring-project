package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
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

    public ResponseEntity<Account> accountLogin(Account account){
        Account registeredAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).orElse(null);

        if(registeredAccount != null && registeredAccount.getUsername().equals(account.getUsername()) && registeredAccount.getPassword().equals(account.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account);
        }
    }

    public boolean isRegisteredUser(int accountID){
        return accountRepository.findById(accountID).isPresent();
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
