package com.oauth.authorized.resource.service;

import com.oauth.authorized.resource.api.dto.UserDto;
import com.oauth.authorized.resource.api.exception.NotFoundException;
import com.oauth.authorized.resource.api.resource.PasswordResetTokenResource;
import com.oauth.authorized.resource.api.resource.UserResource;
import com.oauth.authorized.resource.model.Role;
import com.oauth.authorized.resource.model.domain.PasswordResetToken;
import com.oauth.authorized.resource.model.domain.User;
import com.oauth.authorized.resource.repository.PasswordResetTokenRepository;
import com.oauth.authorized.resource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collection of service methods to create, retrieve or update User objects
 *
 * @author Michał Kaliszewski
 *
 */

@Slf4j
@Service
@Transactional
public class UserService extends AbstractService{

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    private PasswordResetTokenRepository passwordResetTokenRepository;


    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param emailAddress
     *            email address
     * @return Optional object with User object or null if not found
     */
    public Optional<UserResource> findByEmail(String emailAddress) {
                return userRepository.findByEmail(emailAddress).map(
                        (user) -> this.mapperFacade.map(user, UserResource.class));
    }

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param userId
     *            User identifier
     * @return Optional object with User object or null if not found
     */
    public Optional<UserResource> findById(String userId) {
               return userRepository.findById(userId).map(
                        (user) -> this.mapperFacade.map(user, UserResource.class)
        );
    }

    /**
     * Retrieve a customer identified by the given email address.
     *
     * @param userDto
     *            User data required to register user
     * @return Saved or updated User object
     */
    public UserResource saveOrUpdate(final UserDto userDto) {
        Optional<User> savedUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail())
                .map(user -> {
                    this.mapperFacade.map(userDto, user);
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    User user = this.mapperFacade.map(userDto, User.class);
                    user.setPassword(
                            encryptPassword(userDto.getPassword()));
                    if (CollectionUtils.isEmpty(user.getRoles()))
                        user.setRoles(
                                Arrays.stream(this.configurationProperties.getDefaultUserRoles())
                                        .map(Role::valueOf)
                                        .collect(Collectors.toList()));
                    return userRepository.save(user);
                }));

        return savedUser
                .map(user -> this.mapperFacade.map(user, UserResource.class))
                .orElseThrow(IllegalAccessError::new);
    }

    /**
     * Delete a user data.
     *
     * @param user
     *            User data to be deleted
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Delete a user data.
     *
     * @param email
     *            User data to be deleted
     */
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    /**
     * Retrieve a user identified by the given id/email address
     *
     * @param userId
     *            User identifier
     * @param emailAddress
     *            User identifier
     * @return Optional object with User object or null if not found
     */
    public List<UserResource> findUser(String emailAddress, String userId) {
         return (
                     (StringUtils.isNotBlank(emailAddress) || StringUtils.isNotBlank(userId)) ?
                    userRepository.findByIdOrEmail(userId, emailAddress)
                        .map(user -> Collections.singletonList((this.mapperFacade.map(user, UserResource.class))))
                        :
                     Optional.of(this.mapperFacade.mapAsList(userRepository.findAll(), UserResource.class))
                    )
                 .orElse(Collections.emptyList());
    }

    /**
     * Retrieve a user identified by the given id/email address
     *
     * @param orderAndSort
     *            object containing sorting and pagination data
     * @return Optional object with User object or null if not found
     */
    public List<UserResource> findUsers(Pageable orderAndSort) {
        Page<User> users = userRepository.findAll(orderAndSort);
        if (users.getTotalElements() == 0)
            return Collections.emptyList();
        return mapperFacade.mapAsList(users.getContent(), UserResource.class);
    }

    /**
     * Retrieve a user identified by the given id/email address
     *
     * @param email
     *            User email address
     * @return Optional object with User object or null if not found
     */
    public PasswordResetTokenResource generateResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email: %s was not found", email)));
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        return Optional.ofNullable(passwordResetTokenRepository.save(passwordResetToken))
            .map(userToken -> mapperFacade.map(userToken, PasswordResetTokenResource.class))
            .orElseThrow(IllegalAccessError::new);
    }

    /**
     * Retrieve a user identified by the given id/email address
     *
     * @param email
     *            User email address
     * @return Optional object with User object or null if not found
     */
    public void changePassword(String email, String password, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email: %s was not found", email)));
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByTokenAndUser_Id(token, user.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Token for email: %s was not found", email)));
        user.setPassword(encryptPassword(password));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }



    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordResetTokenRepository(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
}
