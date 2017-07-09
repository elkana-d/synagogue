package com.synagogue.service.exception;

public class PersonAlreadyExistsException extends RuntimeException {

    public PersonAlreadyExistsException(final String message) {
        super(message);
    }

}
