package com.wiltech.todos.security.authentication;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wiltech.todos.validation.ValidateUniqueUsername;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * The type Registration request.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationRequest {

    @NotNull
    @Size(max = 80, message = "First name cannot have more than {max} characters")
    private String firstName;

    @NotNull
    @Size(max = 80, message = "Last name cannot have more than {max} characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email
    @ValidateUniqueUsername(message = "{Users.username.NonUnique}")
    private String email;

    @NotNull
    @Size(min = 5, max = 20, message = "Password must be between {min} and {max} characters long. Length found : ${validatedValue.length()}")
    private String password;

    private LocalDate dateOfBirth;
}
