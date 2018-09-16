package com.oauth.authorized.resource.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.oauth.authorized.resource.model.Role;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


public class UserDto implements Serializable {

    private static final long serialVersionUID = 8269421897901384963L;

    private static final int MAX_LENGTH_EMAIL = 100;
    private static final int MAX_LENGTH_PASSWORD = 100;
    private static final int MIN_LENGTH_PASSWORD = 5;


    @ApiModelProperty(value = "The password of the user. Minimum length is 5 characters", required = true)
    @NotEmpty
    @Size(min = MIN_LENGTH_PASSWORD, max = MAX_LENGTH_PASSWORD)
    @JsonDeserialize
    private String password;

    @ApiModelProperty(value = "The E-Mail address of the user.", required = true)
    @NotEmpty
    @Size(max = MAX_LENGTH_EMAIL)
    @Email
    private String email;

    @ApiModelProperty(value = "User roles")
    private List<Role> roles;


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


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
