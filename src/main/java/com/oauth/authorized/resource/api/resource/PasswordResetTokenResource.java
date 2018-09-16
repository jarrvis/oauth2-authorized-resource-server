package com.oauth.authorized.resource.api.resource;

import com.oauth.authorized.resource.api.controller.UserController;
import com.oauth.authorized.resource.api.dto.UserChangePasswordDto;
import com.oauth.authorized.resource.model.domain.User;

import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Resource representation of user's password reset token.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class PasswordResetTokenResource  extends RootResource {


    private String token;

    private User user;

    private LocalDateTime expiryDate;

    public PasswordResetTokenResource() {

        this.add(linkTo(methodOn(
                UserController.class
                ).changePassword(new UserChangePasswordDto())
        ).withSelfRel());

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
