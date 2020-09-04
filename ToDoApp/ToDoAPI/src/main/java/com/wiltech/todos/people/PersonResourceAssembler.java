/*
 * (c) Midland Software Limited 2019
 * Name     : PersonResourceAssembler.java
 * Author   : ferraciolliw
 * Date     : 09 Sep 2019
 */
package com.wiltech.todos.people;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

/**
 * The type Person resource assembler.
 */
@Service
public class PersonResourceAssembler {

    /**
     * Convert to entity person.
     * @param payload the payload
     * @return the person
     */
    public Person convertToEntity(final PersonResource payload) {
        return Person.builder()
                .userId(payload.getUserId())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .phoneNumber(payload.getPhoneNumber())
                .dateOfBirth(payload.getDateOfBirth())
                .gender(payload.getGenderId())
                .maritalStatus(payload.getMaritalStatusId())
                .numberOfDependants(payload.getNumberOfDependants())
                .build();
    }

    /**
     * Convert to dto person resource.
     * @param entity the entity
     * @return the person resource
     */
    public PersonResource convertToDTO(final Person entity) {

        return PersonResource.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .dateOfBirth(entity.getDateOfBirth())
                .genderId(entity.getGender())
                .maritalStatusId(entity.getMaritalStatus())
                .numberOfDependants(entity.getNumberOfDependants())
                .build();
    }

    /**
     * Create links to collection list.
     * @return the list
     */
    public static List<Link> createLinksToCollection() {
        //add self link to the list
        final Link selfRel = linkTo(methodOn(PersonRestService.class)
                .findAll()).withSelfRel();

        //add create/template link to the list
        final Link createLink = linkTo(methodOn(PersonRestService.class)
                .template()).withRel("createPerson");

        return Arrays.asList(selfRel, createLink);
    }

}
