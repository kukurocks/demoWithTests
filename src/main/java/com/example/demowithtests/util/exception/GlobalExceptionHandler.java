package com.example.demowithtests.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body("Country cannot be null");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDate.now(),
                        "Entity not found with id ="+request.getDescription(true),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleResourceWasDeletedException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }
    StringBuilder stringBuilder = new StringBuilder();


    /* @ExceptionHandler(Exception.class)
     public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
         ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
         return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
     }
 */
    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
    }
}
