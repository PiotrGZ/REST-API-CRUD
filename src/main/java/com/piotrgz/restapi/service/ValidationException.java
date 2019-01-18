package com.piotrgz.restapi.service;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
