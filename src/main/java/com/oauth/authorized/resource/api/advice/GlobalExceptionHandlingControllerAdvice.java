package com.oauth.authorized.resource.api.advice;

import com.oauth.authorized.resource.api.exception.AlreadyExistsException;
import com.oauth.authorized.resource.api.exception.InvalidRequestException;
import com.oauth.authorized.resource.api.exception.NotAcceptableException;
import com.oauth.authorized.resource.api.exception.NotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.stream.Collectors;

/**
 * Performs exception handling for all REST API controllers. This class provides exception handlers that respond to
 * possible exceptions with appropriate HTTP status codes for the client.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@ControllerAdvice("com.oauth.authorized.resource.api.controller")
public class GlobalExceptionHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors notFoundExceptionHandler(NotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    VndErrors noAcceptableExceptionHandler(NotAcceptableException ex) {
        return new VndErrors("general_error_error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors alreadyExistsExceptionHandler(AlreadyExistsException ex) {
        return new VndErrors("general_error_error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    VndErrors validationExceptionHandler(InvalidRequestException ex) {

        VndErrors errors = new VndErrors("general_error_message", ex.getMessage());
        if (!CollectionUtils.isEmpty(ex.getErrors().getFieldErrors())) {
            ex.getErrors().getFieldErrors()
                    .stream()
                    .map(error -> new VndErrors.VndError("validation_error", errorMessage(error)))
                    .collect(Collectors.toList())
                    .forEach(errors::add);
         }
        return errors;
    }

    private String errorMessage(FieldError fieldError) {
        return errorMessage(fieldError.getField(), fieldError.getRejectedValue());
    }

    private String errorMessage(String field, Object value) {

        return String.format("Field [%s] validation failed : rejected value [%s]",
                field, value);

    }

}
