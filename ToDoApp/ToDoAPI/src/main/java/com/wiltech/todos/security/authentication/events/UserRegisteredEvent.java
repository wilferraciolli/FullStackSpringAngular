/*
 * (c) Midland Software Limited 2019
 * Name     : UserRegisteredEvent.java
 * Author   : ferraciolliw
 * Date     : 12 Nov 2019
 */
package com.wiltech.todos.security.authentication.events;

import org.springframework.context.ApplicationEvent;

import com.wiltech.todos.security.authentication.RegistrationRequest;

import lombok.Getter;

/**
 * The type User registered event.
 */
@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private RegistrationRequest registrationRequest;

    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     * @param registrationRequest the registration request
     */
    public UserRegisteredEvent(final Object source, final RegistrationRequest registrationRequest) {
        super(source); //Calling this super class constructor is necessary
        this.registrationRequest = registrationRequest;
    }

}
