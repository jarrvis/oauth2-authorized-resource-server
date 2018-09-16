package com.oauth.authorized.resource.api.exception;

/**
 * Easy ParkingAPI exception, thrown when given data is not acceptable.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class NotAcceptableException extends RuntimeException {

    private static final long serialVersionUID = 5991191955244366152L;

    /**
     * Constructor accepting an error message.
     *
     * @param message
     *            message
     */
    public NotAcceptableException(String message) {
        super(message);
    }

}
