package com.oauth.authorized.resource.api.controller;

import com.oauth.authorized.resource.api.dto.UserChangePasswordDto;
import com.oauth.authorized.resource.api.dto.UserDto;
import com.oauth.authorized.resource.api.exception.AlreadyExistsException;
import com.oauth.authorized.resource.api.exception.InvalidRequestException;
import com.oauth.authorized.resource.api.resource.PasswordResetTokenResource;
import com.oauth.authorized.resource.api.resource.UserResource;
import com.oauth.authorized.resource.model.domain.User;
import com.oauth.authorized.resource.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST API endpoints for all user-related service calls, eg. user registration, account cancellation or a given authenticated user,
 * returning all users for admin
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(description = "REST API for performing customer management operations")
public class UserController extends BaseController{

    private UserService userService;


    /**
     * REST endpoint to register a new User.
     *
     * @param userDto User data required to register new User
     * @return User object
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "Registers a new application user", notes = "Registers a new application user.\n", response = UserResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully registered"),
            @ApiResponse(code = 409, message = "User with such email is already registered"),
            @ApiResponse(code = 422, message = "User registration request contains validation errors. Response contains detailed field validation error message"),
            @ApiResponse(code = 428, message = "Database error. Application log needs to be checked")})
    public ResponseEntity<UserResource> registerUser(
            @ApiParam(value = "Model representation of the transferred data to register a new user")  @Valid @RequestBody UserDto userDto,
            BindingResult bindingResult)
    {

        log.debug("User registration request received");

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(String.format("Invalid user registration request, form data contains %s error(s).",
                    bindingResult.getErrorCount()), bindingResult);
        }
        userService.findByEmail(userDto.getEmail()).ifPresent(
                (user) -> {
                    throw new AlreadyExistsException(String.format("User with such email: %s is already registered", userDto.getEmail()));
                }
        );

        return  ResponseEntity.ok(userService.saveOrUpdate(userDto));

    }

    /**
     * REST endpoint to return the customer data of the currently logged in / authenticated user.
     *
     * This service requires the user to be authenticated via the <strong>OAuth2</strong>. Authorization is
     * granted by Spring Security and ensured via <i>PreAuthorize</i> annotation.
     *
     * @return User object
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Get customer data", notes = "Gets the customer data belonging to the authenticated user.\n", response = UserResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User data is returned"),
            @ApiResponse(code = 401, message = "The token is invalid, expired or wasn't found") })
    public ResponseEntity<UserResource> getUser() {

        log.debug("Get user data request received");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getPrincipal().toString())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }


    /**
     * REST endpoint to update the user data of the currently logged in / authenticated user.
     *
     * This service requires the user to be authenticated via the <strong>OAuth2</strong>. Authorization is
     * granted by Spring Security and ensured via <i>PreAuthorize</i> annotation.
     *
     * @return User object
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Updates registered user data", notes = "Updates the data belonging to the <b>authenticated</b> user.\n", response = UserResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User data successfully updated"),
            @ApiResponse(code = 401, message = "The token is invalid, expired or wasn't found"),
            @ApiResponse(code = 422, message = "User data update request contains validation errors. Response contains detailed field validation error message"),
            @ApiResponse(code = 428, message = "Database error. Application log needs to be checked")})
    public ResponseEntity<UserResource> updateUser(
            @ApiParam(value = "Model representation of the transferred data to update user data")  @Valid @RequestBody UserDto userDto,
            BindingResult bindingResult)
    {

        log.debug("User update request received");

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(String.format("Invalid user update request, form data contains %s error(s).",
                    bindingResult.getErrorCount()), bindingResult);
        }

        return  ResponseEntity.ok(userService.saveOrUpdate(userDto));

    }

    /**
     * REST endpoint to return the customer data of the currently logged in / authenticated user.
     *
     * This service requires the user to be authenticated via the <strong>OAuth2</strong>. Authorization is
     * granted by Spring Security and ensured via <i>PreAuthorize</i> annotation.
     *
     * @return User object
     */
    @RequestMapping(value ="/{userEmail}/reset-password", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Reset user password", notes = "Generates a <b>reset password token</b> and sends it via email to user. " +
            "Currently token is returned in response as a temporary solution\n")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password reset token generated"),
            @ApiResponse(code = 404, message = "User was not found for given email") })
    public ResponseEntity<PasswordResetTokenResource> resetPassword(
            @ApiParam(value = "Model representation of the transferred data to update user data", required = true) @PathVariable("userEmail") String userEmail
    ) {

        log.debug("Reset password request received");

        return ResponseEntity.ok(this.userService.generateResetPasswordToken(userEmail));
    }

    /**
     * REST endpoint to return the customer data of the currently logged in / authenticated user.
     *
     * This service requires the user to be authenticated via the <strong>OAuth2</strong>. Authorization is
     * granted by Spring Security and ensured via <i>PreAuthorize</i> annotation.
     *
     * @return User object
     */
    @RequestMapping(value ="/change-password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Change user password", notes = "Generates a <b>reset password token</b> and sends it via email to user. " +
            "Currently token is returned in response as a temporary solution\n")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Password reset token generated"),
            @ApiResponse(code = 404, message = "User was not found for given email"),
            @ApiResponse(code = 409, message = "Reset password token is incorrect"),})
    public ResponseEntity<Void> changePassword(
            @ApiParam(value = "Model representation of the transferred data to update user data") @RequestBody @Valid UserChangePasswordDto changePasswordDto
    ) {

        log.debug("Change password request received");

        userService.changePassword(changePasswordDto.getEmail(), changePasswordDto.getPassword(), changePasswordDto.getToken());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     *
     * Retrieve a list of all registered users.
     *
     * @param emailAddress Email address of specific user to be fetched
     * @param userId Id of the specific user to be fetched
     * @param limit Integer limiting the results list
     * @param sortBy Name of User data field by which results should be sorted. Use '-' to get negative sort
     * @return list of Users objects
     *
     * TODO: pagination
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all registered users", notes = "'ADMIN' role is required. Lists all <b>users</b> that were registered "
            + "during user registration process.  \n Empty list is returned with status code 200 "
            +  "in case no users found that match search criteria. Default number of results is 10, "
            +  "can be influenced by 'limit' parameter. Default sort option is user date and time of registration. TODO: pagination", response = UserResource.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A pageable list of all registered users"),
            @ApiResponse(code = 406, message = "In case of validation errors on the sortBy or limit parameters")})
    public List<UserResource> getUsers(
            @ApiParam(value = "Email address of specific user to be fetched") @RequestParam(value = "emailAddress", required = false)  final String emailAddress,
            @ApiParam(value = "Id of the specific user to be fetched") @RequestParam(value = "userId", required = false) final String userId,
            @ApiParam(value = "Limit the results list") @RequestParam(value = "limit", required = false) final String limit,
            @ApiParam(value = "Name of User data field by which results should be sorted. Use '-' to get negative sort") @RequestParam(value = "sortBy", required = false) final String sortBy
    )
    {
        log.debug("Get users request received");

        Optional<PageRequest> orderAndSort = getPageRequestOptional(limit, sortBy, User.class);

        return orderAndSort
                .map(pageRequest -> userService.findUsers(pageRequest))
                .orElseGet(() -> userService.findUser(emailAddress, userId));
    }

    /**
     * REST endpoint to delete the user data of the currently logged in / authenticated user.
     *
     * This service requires the user to be authenticated via the <strong>OAuth2</strong>. Authorization is
     * granted by Spring Security and ensured via <i>PreAuthorize</i> annotation.
     *
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a User object", notes = "Deletes a <b>User</b> data belonging to the <b>authenticated</b> user.\n")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted"),
            @ApiResponse(code = 401, message = "The token is invalid, expired or wasn't found"),
            @ApiResponse(code = 428, message = "Database error. Application log needs to be checked")})
    public ResponseEntity<Void> deleteUser() {

        log.debug("Delete user request received");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.delete(auth.getPrincipal().toString());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Autowired
    protected void setUserService(UserService userService) {
        this.userService = userService;
    }

}
