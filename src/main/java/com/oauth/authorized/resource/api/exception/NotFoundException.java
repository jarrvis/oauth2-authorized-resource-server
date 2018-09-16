package com.oauth.authorized.resource.api.exception;


/**
 * Easy ParkingAPI exception, thrown when given data is not acceptable.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String name) {

        super(
                String.format("Entity %s not found.", name)
        );

    }
}
