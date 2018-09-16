package com.oauth.authorized.resource.api.controller;

import com.oauth.authorized.resource.api.resource.RootResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Collection of API endpoint strings for eg. the account management area of for Spring Actuator endpoints.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public abstract class ApiController {

    public static final String API_ACCOUNT_PATH = "/account";

    public static final String AUTHENTICATE_URL = API_ACCOUNT_PATH + "/manage";

    // Spring Boot Actuator services
    public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
    public static final String BEANS_ENDPOINT = "/beans";
    public static final String CONFIGPROPS_ENDPOINT = "/configprops";
    public static final String DUMP_ENDPOINT = "/dump";
    public static final String ENV_ENDPOINT = "/env";
    public static final String HEALTH_ENDPOINT = "/health";
    public static final String INFO_ENDPOINT = "/info";
    public static final String METRICS_ENDPOINT = "/metrics";
    public static final String MAPPINGS_ENDPOINT = "/mappings";
    public static final String SHUTDOWN_ENDPOINT = "/shutdown";
    public static final String TRACE_ENDPOINT = "/trace";

    private ApiController() {
    }

    @RequestMapping(path = "/api", method = RequestMethod.GET)
    public RootResource getRoot() {
        return new RootResource();
    }
}
