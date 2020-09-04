package com.wiltech.todos.providers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Service;

@Service
public class ProviderResourceAssembler {

    @Autowired
    private EntityLinks entityLinks;

    //Ps it can also be used with entity links
    //            System.out.println(entityLinks.linkForSingleResource(PersonNew .class, employee.getId()));
    //        System.out.println(entityLinks.linkToCollectionResource(PersonNew.class));

    public Provider convertToEntity(final ProviderResource payload) {
        return Provider.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .baseUrl(payload.getBaseUrl())
                .clientId(payload.getClientId())
                .clientSecret(payload.getClientSecret())
                .username(payload.getUsername())
                .password(payload.getPassword())
                .email(payload.getEmail())
                .website(payload.getWebsite())
                .enabled(payload.getEnabled())
                .build();
    }

    public ProviderResource transpose(final Provider entity) {
        return ProviderResource.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .baseUrl(entity.getBaseUrl())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .website(entity.getWebsite())
                .enabled(entity.getEnabled())
                .build();
    }

    //    public static Resource<ProviderResource> createAndAddLinksToResource(final ProviderResource resource) {
    //        //link to self
    //        final Link linkToGetSingle = linkTo(methodOn(ProviderRestService.class)
    //                .findById(resource.getId())).withSelfRel();
    //
    //        //link to update resource
    //        final Link linkToUpdateSingle = linkTo(methodOn(ProviderRestService.class)
    //                .findById(resource.getId())).withRel("updateProvider");
    //
    //        //link to update resource
    //        final Link linkToDeleteSingle = linkTo(methodOn(ProviderRestService.class)
    //                .findById(resource.getId())).withRel("deleteProvider");
    //
    //        return new Resource<>(resource, linkToGetSingle, linkToUpdateSingle, linkToDeleteSingle);
    //    }

    public static List<Link> createLinksToCollection() {
        //add self link to the list
        final Link selfRel = linkTo(methodOn(ProviderRestService.class)
                .findAll()).withSelfRel();

        //add create/tempalte link to the list
        final Link createLink = linkTo(methodOn(ProviderRestService.class)
                .template()).withRel("createProvider");

        return Arrays.asList(selfRel, createLink);
    }

}
