/*
 * (c) Midland Software Limited 2019
 * Name     : UserCreatedEvent.java
 * Author   : ferraciolliw
 * Date     : 13 Nov 2019
 */
package com.wiltech.todos.users.events;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * The type User created event.
 */
@Getter
public class UserCreatedEvent extends ApplicationEvent {

    @NotNull
    private Long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    private LocalDate dateOfBirth;

    /**
     * Instantiates a new User created event.
     * @param source the object on which the event initially occurred (never {@code null})
     * @param userId the user id
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @param dateOfBirth the date of birth
     */
    public UserCreatedEvent(final Object source, @NotNull final Long userId, @NotNull final String firstName,
            @NotNull final String lastName, @NotNull final String email, final LocalDate dateOfBirth) {
        super(source);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

}
