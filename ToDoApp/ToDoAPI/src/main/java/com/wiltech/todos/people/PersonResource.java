package com.wiltech.todos.people;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Value;

/**
 * The type Person resource.
 */
@Value
@Builder
@JsonTypeName("person")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PersonResource {

    private Long id;

    private Long userId;

    @NotNull(message = "User name cannot be null.")
    private String firstName;

    @NotNull(message = "User name cannot be null.")
    private String lastName;

    @NotNull(message = "Email name cannot be null.")
    private String email;

    private LocalDate dateOfBirth;
    private PersonGenderType genderId;
    private PersonMaritalStatusType maritalStatusId;
    private Integer numberOfDependants;
    private String phoneNumber;

}
