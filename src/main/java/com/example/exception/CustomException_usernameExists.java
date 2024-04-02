package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException_usernameExists extends RuntimeException{
    public CustomException_usernameExists(){
        super();
    }
    
    @ExceptionHandler(CustomException_usernameExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String CustomException_usernameExists_feedback(String unallowedUsername){
        return "The username " + unallowedUsername + " already exists";
    }
}