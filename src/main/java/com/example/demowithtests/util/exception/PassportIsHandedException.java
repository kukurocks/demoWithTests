package com.example.demowithtests.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassportIsHandedException extends RuntimeException {
    public PassportIsHandedException(String s) {
        super(s);
    }
}
