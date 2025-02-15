package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceWasDeletedException extends RuntimeException {
}
