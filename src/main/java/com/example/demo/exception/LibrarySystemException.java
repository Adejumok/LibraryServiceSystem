package com.example.demo.exception;

import lombok.Getter;

@Getter
public class LibrarySystemException extends RuntimeException{
    private int statusCode;

    public LibrarySystemException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
