package com.example.demo.controller.exceptionHandler;

import com.example.demo.exception.LibrarySystemException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class LibraryManagementSystemExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LibrarySystemException.class)
    public ResponseEntity<?> handleCustomerAlreadyExistException(LibrarySystemException customerAlreadyExistException){
        ApiError apiError = ApiError.builder()
                .message(customerAlreadyExistException.getMessage())
                .successful(false)
                .statusCode(customerAlreadyExistException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }
    @ExceptionHandler(UnirestException.class)
    public ResponseEntity<?> handleUnirestException( UnirestException unirestException){
        ApiError apiError = ApiError.builder()
                .message(unirestException.getMessage())
                .successful(false)
                .statusCode(400)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }
}
