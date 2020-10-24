package com.codingchallenge.adtech.exception;

public class PlatformException extends Exception {

    private Exception exception;

    public PlatformException(Exception ex) {
        super(ex.getMessage());
        this.exception = ex;
    }

}
