package com.oauth.authorized.resource.repository;

import com.oauth.authorized.resource.model.domain.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Collection of repository methods to create, retrieve or update User objects
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

    /**
     * Retrieve a password reset token.
     *
     * @param token
     *            password reset token
     * @param id
     *            user id - for security to confirm that token belongs to user
     * @return Optional object with PasswordResetToken object or null if not found
     */
    Optional<PasswordResetToken> findByTokenAndUser_Id(String token, String id);

    /**
     * Delete all password reset tokens before specified date.
     *
     * @param date
     *            password reset token
     */
    void deleteByExpiryDateBefore(LocalDateTime date);




}
