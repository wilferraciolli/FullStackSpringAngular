package com.wiltech.todos.security.authentication;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * The type Authentication request.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;
}
