package com.oauth.authorized.resource.api.exception;

/**
 * API exception, thrown when resource already exists e.g. user tries to register with email that belongs to already registered user.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 7527456015412942400L;

    /**
     * Constructor accepting an error message.
     *
     * @param message message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}
