package com.wiltech.todos.exceptions;

/**
 * Class to be used as exception of Entity not found.
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
