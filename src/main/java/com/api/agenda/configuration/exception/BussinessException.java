package com.api.agenda.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BussinessException extends RuntimeException{
    public BussinessException(String message){
        super(message);
    }
}
