package com.example.service;

import java.net.http.HttpTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Component
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Message> addMessage(Message message){
        AccountService accountService = new AccountService(accountRepository);
        String messageText = message.getMessage_text();
        boolean isRegisteredUser = accountService.isRegisteredUser(message.getPosted_by());

        if(messageText.length() > 0 && messageText.length() < 256 && isRegisteredUser){
            Message newMessage = messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
