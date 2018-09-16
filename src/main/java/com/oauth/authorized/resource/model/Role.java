package com.oauth.authorized.resource.model;

/**
 * Enumeration of user roles.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public enum Role {

    ADMIN,
    CUSTOMER,
    PARTNER;

    public static class Constants {
        public static final String ADMIN_CMD = "hasRole('ADMIN')";
        public static final String CUSTOMER_CMD = "hasRole('CUSTOMER')";
        public static final String PARTNER_CMD = "hasRole('PARTNER')";
    }
}
