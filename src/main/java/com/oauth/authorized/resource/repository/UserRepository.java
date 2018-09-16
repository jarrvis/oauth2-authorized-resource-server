package com.oauth.authorized.resource.repository;

import com.oauth.authorized.resource.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Collection of repository methods to create, retrieve or update User objects
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param emailAddress
     *            email address
     * @return Optional object with User object or null if not found
     */
    Optional<User> findByEmail(String emailAddress);

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param userId
     *            User identifier
     * @return Optional object with User object or null if not found
     */
    Optional<User> findById(String userId);

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param userId
     *            User identifier
     * @param emailAddress
     *            User identifier
     * @return Optional object with User object or null if not found
     */
    Optional<User> findByIdAndEmail(String userId, String emailAddress);

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param userId
     *            User identifier
     * @param emailAddress
     *            User identifier
     * @return Optional object with User object or null if not found
     */
    Optional<User> findByIdOrEmail(String userId, String emailAddress);

    /**
     * Retrieve a list of users based on limit and sort options
     *
     * @return List of User objects or null if not found
     */
    Page<User> findAll(Pageable sortAndLimit);

    /**
     * Delete a user identified by the given email address.
     *
     * @param email
     *            User email
     */
    void deleteByEmail(String email);

}
