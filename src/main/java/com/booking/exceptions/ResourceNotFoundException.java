package com.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5355813802451798706L;

    public ResourceNotFoundException(final String type, final Long id) {
        super(String.format("[%s] not found by id: [%d]", type, id));
    }
}
