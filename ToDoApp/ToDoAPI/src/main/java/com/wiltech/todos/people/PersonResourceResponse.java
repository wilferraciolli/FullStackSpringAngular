package com.wiltech.todos.people;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wiltech.BaseDTO;
import com.wiltech.Meta;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Person resource response.
 */
@Data
@Builder
@NoArgsConstructor
@Relation(value = "person", collectionRelation = "collection")
public class PersonResourceResponse extends BaseDTO {

    @JsonProperty("_data")
    private PersonResource data;

    /**
     * Instantiates a new Person resource response.
     * @param resource the person resource
     */
    public PersonResourceResponse(final PersonResource resource) {
        this.data = resource;
        final Long id = resource.getId();

        addLinks(id);
    }

    public PersonResourceResponse(final PersonResource resource, final Meta meta) {
        this.data = resource;
        super.setMeta(meta);
        final Long id = resource.getId();

        addLinks(id);
    }

    private void addLinks(final Long id) {
        if (Objects.nonNull(id)) {
            add(linkTo(methodOn(PersonRestService.class).findById(id)).withSelfRel());
            add(linkTo(methodOn(PersonRestService.class).findById(id)).withRel("updatePerson"));
            add(linkTo(methodOn(PersonRestService.class).findById(id)).withRel("deletePerson"));
        }

        add(linkTo(PersonRestService.class).withRel("people"));
    }
}
