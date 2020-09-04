package com.wiltech.todos.people;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Person.
 */
@Entity
@Table(name = "person")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotEmpty
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private PersonGenderType gender;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private PersonMaritalStatusType maritalStatus;

    private Integer numberOfDependants;

    /**
     * Update person.
     * @param personResource the person resource
     */
    public void updatePerson(final PersonResource personResource) {
        this.firstName = personResource.getFirstName();
        this.lastName = personResource.getLastName();
        this.email = personResource.getEmail();
        this.gender = personResource.getGenderId();
        this.phoneNumber = personResource.getPhoneNumber();
        this.dateOfBirth = personResource.getDateOfBirth();
        this.maritalStatus = personResource.getMaritalStatusId();
        this.numberOfDependants = personResource.getNumberOfDependants();
    }
}
