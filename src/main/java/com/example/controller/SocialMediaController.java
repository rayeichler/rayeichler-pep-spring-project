package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
      this.accountService = accountService;
      this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> addAccount(@RequestBody Account account){
      return accountService.addAccount(account);
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> accountLogin(@RequestBody Account account){
      return accountService.accountLogin(account);
    }

    @GetMapping("messages")
    public @ResponseBody ResponseEntity<String> getAllMessages() throws JsonProcessingException{
      ObjectMapper om = new ObjectMapper();
      String json = om.writeValueAsString(messageService.getAllMessages());
      return ResponseEntity.status(HttpStatus.OK).body(json);
    }

     @GetMapping("accounts/{account_id}/messages")
     public @ResponseBody ResponseEntity<String> getMessagesByUser(@PathVariable("account_id") Integer account_id) throws JsonProcessingException{
       ObjectMapper om = new ObjectMapper();
       String json = om.writeValueAsString(messageService.getMessagesByUser(account_id));
       return ResponseEntity.status(HttpStatus.OK).body(json);
     }

    @PostMapping("messages") 
    public @ResponseBody ResponseEntity<Message> addMessage(@RequestBody Message message){
      return messageService.addMessage(message);
    }

    @GetMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer message_id){
      return messageService.getMessageById(message_id);
    }

    @PatchMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable("message_id") Integer messageID, @RequestBody Message newMessage){
      return messageService.updateMessage(messageID, newMessage);
    }

    @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") Integer message_id){
      return messageService.deleteMessageById(message_id);
    }
}