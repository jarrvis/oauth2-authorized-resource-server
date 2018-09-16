package com.oauth.authorized.resource.model.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Model representation of user's password reset token.
 * Kept in separate document so that there is no need for updating user model every time token is generated or expires
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Document(collection = "reset_pass_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotEmpty
    private String token;

    @DBRef
    private User user;

    @NotNull
    private LocalDateTime expiryDate;

    public PasswordResetToken (){

    }

    public PasswordResetToken (User user, String token){
        this.user = user;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
