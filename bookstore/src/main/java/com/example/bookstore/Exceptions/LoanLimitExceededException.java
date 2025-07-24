package com.example.bookstore.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanLimitExceededException extends RuntimeException{
    public LoanLimitExceededException(String message){
        super(message);
    }
}
