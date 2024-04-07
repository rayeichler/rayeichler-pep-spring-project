package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public ResponseEntity<Message> getMessageById(Integer messageID){
        return ResponseEntity.status(HttpStatus.OK).body(messageRepository.findById(messageID).orElse(null));
    }

    public List<Message> getMessagesByUser(Integer account_id){
         return messageRepository.findAllByPostedBy(account_id);
     }

    public ResponseEntity<Integer> deleteMessageById(Integer messageID){
        if(messageRepository.existsById(messageID)){
            messageRepository.deleteById(messageID);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
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

    public ResponseEntity<Integer> updateMessage(Integer messageID, Message newMessage){
        String newText = newMessage.getMessage_text();
        Message oldMessage = messageRepository.findById(messageID).orElse(null);
        if(oldMessage != null && newText.length() > 0 && newText.length() < 256){
            oldMessage.setMessage_text(newText);
            messageRepository.save(oldMessage);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
