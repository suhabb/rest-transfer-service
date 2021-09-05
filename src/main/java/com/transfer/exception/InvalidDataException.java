package com.transfer.exception;



public class InvalidDataException extends RuntimeException {

    private String message;

    public InvalidDataException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage(){
        return this.message;
    }
}
