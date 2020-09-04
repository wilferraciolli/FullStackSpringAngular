package com.wiltech.todos.users;

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
 * The type User resource response.
 */
@Data
@Builder
@NoArgsConstructor
@Relation(value = "user", collectionRelation = "collection")
public class UserResourceResponse extends BaseDTO {

    @JsonProperty("_data")
    private UserResource data;

    /**
     * Instantiates a new User resource response.
     * @param resource the resource
     */
    public UserResourceResponse(final UserResource resource) {
        this.data = resource;
        final Long id = resource.getId();

        addLinks(id);
    }

    /**
     * Instantiates a new User resource response.
     * @param resource the resource
     * @param meta the meta
     */
    public UserResourceResponse(final UserResource resource, final Meta meta) {
        this.data = resource;
        super.setMeta(meta);
        final Long id = resource.getId();

        addLinks(id);
    }

    private void addLinks(final Long id) {
        if (Objects.nonNull(id)) {
            add(linkTo(methodOn(UserRestService.class).findById(id)).withSelfRel());
            add(linkTo(methodOn(UserRestService.class).findById(id)).withRel("updateUser"));
            add(linkTo(methodOn(UserRestService.class).findById(id)).withRel("deleteUser"));
        }

        add(linkTo(UserRestService.class).withRel("users"));
    }
}
