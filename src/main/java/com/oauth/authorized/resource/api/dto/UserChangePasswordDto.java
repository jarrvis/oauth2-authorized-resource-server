package com.oauth.authorized.resource.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class UserChangePasswordDto implements Serializable {

    private static final long serialVersionUID = 8269421897912384963L;

    private static final int MAX_LENGTH_EMAIL = 100;
    private static final int MAX_LENGTH_PASSWORD = 100;
    private static final int MIN_LENGTH_PASSWORD = 5;

    @ApiModelProperty(value = "The password of the user. Minimum length is 5 characters", required = true)
    @NotEmpty
    @Size(min = MIN_LENGTH_PASSWORD, max = MAX_LENGTH_PASSWORD)
    private String password;

    @ApiModelProperty(value = "Password confirmation. Must be same as 'password'", required = true)
    @NotEmpty
    private String confirmPassword;

    @ApiModelProperty(value = "The E-Mail address of the user.", required = true)
    @NotEmpty
    @Size(max = MAX_LENGTH_EMAIL)
    @Email
    private String email;

    @ApiModelProperty(value = "User change password token.", required = true)
    @NotEmpty
    private String token;

    @AssertTrue(message="confirmPassword field should be equal than password field")
    private boolean isValid() {
        return this.password.equals(this.confirmPassword);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
