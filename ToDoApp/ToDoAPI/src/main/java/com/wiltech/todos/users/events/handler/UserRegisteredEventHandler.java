package com.wiltech.todos.users.events.handler;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.wiltech.todos.security.authentication.RegistrationRequest;
import com.wiltech.todos.security.authentication.events.UserRegisteredEvent;
import com.wiltech.todos.users.UserAppService;
import com.wiltech.todos.users.UserResource;
import com.wiltech.todos.users.UserRoleType;

/**
 * The type User registered event handler.
 */
@Service
public class UserRegisteredEventHandler implements ApplicationListener<UserRegisteredEvent> {

    @Autowired
    private UserAppService userAppService;

    @Override
    public void onApplicationEvent(final UserRegisteredEvent event) {

        final RegistrationRequest userRegisteredDetails = event.getRegistrationRequest();

        this.createUser(userRegisteredDetails);
    }

    private void createUser(final RegistrationRequest user) {

        this.userAppService.create(UserResource.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .active(false)
                .roleIds(asList(UserRoleType.ROLE_USER.name()))
                .build());
    }
}
