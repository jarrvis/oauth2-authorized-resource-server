package com.oauth.authorized.resource.api.validator;

import com.oauth.authorized.resource.api.exception.NotFoundException;
import com.oauth.authorized.resource.model.domain.User;
import com.oauth.authorized.resource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@Slf4j
public class ValidatingUserRepositoryDecorator {

    private UserRepository userRepository;

    @Autowired
    public ValidatingUserRepositoryDecorator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findAccountValidated(String emailAddress) {
        log.debug("findAccountValidated " + emailAddress);
        
        Optional<User> userOptional = userRepository.findByEmail(emailAddress);

        return userOptional.orElseThrow(
                () -> new NotFoundException(emailAddress)
        );
    }

}
