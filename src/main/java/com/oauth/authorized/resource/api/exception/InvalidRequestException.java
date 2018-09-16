package com.oauth.authorized.resource.api.exception;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * API exception, thrown when a request is not invalid in the current context.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = -3445333324593174117L;

    private final Errors errors;


    /**
     * Constructor accepting an error message, an Errors object.
     *
     * @param message message
     * @param errors  Errors object
     */
    public InvalidRequestException(String message, Errors errors) {
        super(message);
        Assert.notNull(errors, "Errors must not be null!");
        this.errors = errors;
    }

    /**
     * Returns the stored Errors object.
     *
     * @return Errors object
     */
    public Errors getErrors() {
        return errors;
    }
}