package com.wiltech.todos.providers;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Value;

/**
 * The type Provider resource.
 */
@Value
@Builder
@JsonTypeName("provider")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProviderResource implements Serializable {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 255, message = "Name '${validatedValue}' must be between {min} and {max} characters long. Length found : ${validatedValue.length()}")
    private String name;

    @Size(max = 2000, message = "Description cannot have more than {max} characters")
    private String description;

    @NotNull(message = "Base URL cannot be null")
    @Size(max = 2000, message = "Base URL cannot have more than {max} characters")
    private String baseUrl;

    @Size(max = 255, message = "Client Id cannot have more than {max} characters")
    private String clientId;

    @Size(max = 255, message = "Client Secret cannot have more than {max} characters")
    private String clientSecret;

    @Size(max = 80, message = "Username cannot have more than {max} characters")
    private String username;

    @Size(max = 80, message = "Password cannot have more than {max} characters")
    private String password;

    @Email
    @Size(max = 80, message = "Email cannot have more than {max} characters")
    private String email;

    @Size(max = 255, message = "Web site cannot have more than {max} characters")
    private String website;

    private Boolean enabled;

}
