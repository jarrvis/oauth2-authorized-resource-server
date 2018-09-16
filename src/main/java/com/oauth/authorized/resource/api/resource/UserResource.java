package com.oauth.authorized.resource.api.resource;

import com.oauth.authorized.resource.api.controller.UserController;
import org.springframework.hateoas.ResourceSupport;
import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * User resource containing user resource data
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class UserResource extends ResourceSupport {

    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String roles[];

    public UserResource() {

        this.add(linkTo(methodOn(
                UserController.class
                ).getUser()
        ).withSelfRel());

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
