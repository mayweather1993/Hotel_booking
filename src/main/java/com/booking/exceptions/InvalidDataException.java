package com.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = -3318354594173548539L;

    public InvalidDataException(final String message) {
        super(message);
    }
}
