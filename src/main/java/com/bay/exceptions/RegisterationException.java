package com.bay.exceptions;

import org.springframework.http.HttpStatus;

public class RegisterationException extends SystemException {

    public RegisterationException(String message) {
        super(message);
    }

    public RegisterationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
