package com.wiltech.todos.users.profile;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Value;

/**
 * The type User profile resource.
 */
@Value
@Builder
@JsonTypeName("userProfile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserProfileResource extends RepresentationModel {

    private Long userId;
    private Long personId;
    private String firstName;
    private String lastName;
}
