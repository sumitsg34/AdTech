package com.codingchallenge.adtech.exception;

public class DataNotFoundException extends Exception {

    private String message;

    public DataNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
