package com.example.demowithtests.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ListEmptyException.class)
    protected ResponseEntity<MyExceptionHandler> handleListEmptyException() {
        return new ResponseEntity<>(new MyExceptionHandler("No users were found"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<MyExceptionHandler> handleUserNotFoundException() {
        return new ResponseEntity<>(new MyExceptionHandler("The user does not exist"), HttpStatus.BAD_REQUEST);
    }


    @Data
    @AllArgsConstructor
    private static class MyExceptionHandler {
        private String message;
    }
}
