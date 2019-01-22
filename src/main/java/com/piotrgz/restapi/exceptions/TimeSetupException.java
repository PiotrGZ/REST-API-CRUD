package com.piotrgz.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class TimeSetupException extends IllegalArgumentException {
    public TimeSetupException(String s) {
        super(s);
    }
}
