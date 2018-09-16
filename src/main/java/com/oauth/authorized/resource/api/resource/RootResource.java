package com.oauth.authorized.resource.api.resource;

import com.oauth.authorized.resource.api.controller.UserController;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Base resource containing common resource data
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class RootResource extends ResourceSupport {

    public RootResource() {

        add(
                linkTo(UserController.class).withRel("users")
        );

    }
}
