package com.shoppingManagement.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends Throwable{

    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
