package com.wiltech.todos.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import com.wiltech.todos.users.user.User;

/**
 * The type User resource assembler.
 */
@Service
public class UserResourceAssembler {

    /**
     * Convert to entity user.
     * @param payload the payload
     * @return the user
     */
    public User convertToEntity(final UserResource payload) {
        return User.builder()
                .username(payload.getUsername())
                .password(payload.getPassword())
                .active(payload.getActive())
                .roles(payload.getRoleIds())
                .build();
    }

    //    /**
    //     * Convert to dto user resource.
    //     * @param entity the entity
    //     * @return the user resource
    //     */
    //    public UserResource convertToDTO(final UserDetailsView entity) {
    //
    //        return UserResource.builder()
    //                .id(entity.getId())
    //                .firstName(entity.getFirstName())
    //                .lastName(entity.getLastName())
    //                .username(entity.getUsername())
    //                .password("***")
    //                .dateOfBirth(entity.getDateOfBirth())
    //                .active(entity.getActive())
    //                .roleIds(getRoles(entity))
    //                .build();
    //    }

    public UserResource convertToDTO(final UserDetailsView entity, final List<String> roleIds) {

        return UserResource.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .password("***")
                .dateOfBirth(entity.getDateOfBirth())
                .active(entity.getActive())
                .roleIds(roleIds)
                .build();
    }

    //    public static Resource<UserResource> createAndAddLinksToResource(final UserResource resource) {
    //        //link to self
    //        final Link linkToGetSingle = linkTo(methodOn(UserRestService.class)
    //                .findById(resource.getId())).withSelfRel();
    //
    //        //link to update resource
    //        final Link linkToUpdateSingle = linkTo(methodOn(ProviderRestController.class)
    //                .findById(resource.getId())).withRel("update");
    //
    //        //link to update resource
    //        final Link linkToDeleteSingle = linkTo(methodOn(ProviderRestController.class)
    //                .findById(resource.getId())).withRel("delete");
    //
    //        return new Resource<>(resource, linkToGetSingle, linkToUpdateSingle, linkToDeleteSingle);
    //    }

    /**
     * Create links to collection list.
     * @return the list
     */
    public static List<Link> createLinksToCollection() {
        //add self link to the list
        final Link selfRel = linkTo(methodOn(UserRestService.class)
                .findAll()).withSelfRel();

        //add create/template link to the list
        final Link createLink = linkTo(methodOn(UserRestService.class)
                .template()).withRel("createUser");

        return Arrays.asList(selfRel, createLink);
    }

    private List<String> getRoles(final UserDetailsView entity) {
        return new ArrayList<>();
        //        return entity.getRoleIds().stream()
        //                .map(role -> role.get())
        //                .collect(Collectors.toList());
    }
}
