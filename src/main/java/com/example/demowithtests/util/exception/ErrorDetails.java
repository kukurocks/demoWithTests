package com.example.demowithtests.util.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ErrorDetails {
    private final LocalDate timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(LocalDate timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
