package com.example.demowithtests.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PassportIsHandedException extends RuntimeException {
    public PassportIsHandedException(String message) {
        super(message);
    }
}
