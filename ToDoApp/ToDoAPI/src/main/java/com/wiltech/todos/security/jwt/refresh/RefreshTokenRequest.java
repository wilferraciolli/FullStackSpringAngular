package com.wiltech.todos.security.jwt.refresh;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequest implements Serializable {

    @NotNull
    private String refreshToken;
    private String userId;
}
