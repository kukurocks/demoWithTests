package com.example.demowithtests.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WorkPlaceIsHandedException extends RuntimeException {
    public WorkPlaceIsHandedException(String message) {
        super(message);
    }
}
