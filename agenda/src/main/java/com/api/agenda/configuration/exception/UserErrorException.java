package com.api.agenda.configuration.exception;

public class UserErrorException extends RuntimeException{
    public UserErrorException(String message){
        super(message);
    }
}
