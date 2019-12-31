package com.bay.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SystemException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
