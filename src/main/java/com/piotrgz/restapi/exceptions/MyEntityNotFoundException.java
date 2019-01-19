package com.piotrgz.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MyEntityNotFoundException extends IllegalArgumentException {

    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
