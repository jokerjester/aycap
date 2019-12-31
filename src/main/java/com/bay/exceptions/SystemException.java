package com.bay.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SystemException extends Exception {
    private HttpStatus httpStatus;

    public SystemException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public SystemException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
