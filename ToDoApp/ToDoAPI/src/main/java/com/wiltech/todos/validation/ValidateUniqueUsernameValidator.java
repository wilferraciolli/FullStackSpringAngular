package com.wiltech.todos.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wiltech.todos.users.user.UserRepository;

@Component
public class ValidateUniqueUsernameValidator implements ConstraintValidator<ValidateUniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    private boolean allowBlanks;

    @Override
    public void initialize(ValidateUniqueUsername constraintAnnotation) {
        allowBlanks = constraintAnnotation.allowBlanks();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        boolean result = false;

        // check if blank or empty
        if (StringUtils.isBlank(value)) {

            return allowBlanks;
        } else {

            return Long.valueOf(0L).equals(userRepository.checkUsernameExists(value));
        }
    }
}
