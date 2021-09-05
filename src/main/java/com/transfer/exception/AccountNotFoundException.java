package com.transfer.exception;



public class AccountNotFoundException extends RuntimeException {

    private String message;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage(){
        return this.message;
    }
}
