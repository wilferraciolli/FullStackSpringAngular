package com.wiltech.todos.providers;

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
 * The type Provider resource response.
 */
@Data
@Builder
@NoArgsConstructor
@Relation(value = "provider", collectionRelation = "collection")
public class ProviderResourceResponse extends BaseDTO {

    @JsonProperty("_data")
    private ProviderResource data;

    /**
     * Instantiates a new Provider resource response.
     * @param resource the resource
     */
    public ProviderResourceResponse(final ProviderResource resource) {
        this.data = resource;
        final Long id = resource.getId();

        addLinks(id);
    }

    /**
     * Instantiates a new Provider resource response.
     * @param resource the resource
     * @param meta the meta
     */
    public ProviderResourceResponse(final ProviderResource resource, final Meta meta) {
        this.data = resource;
        super.setMeta(meta);
        final Long id = resource.getId();

        addLinks(id);
    }

    private void addLinks(final Long id) {
        if (Objects.nonNull(id)) {
            add(linkTo(methodOn(ProviderRestService.class).findById(id)).withSelfRel());
            add(linkTo(methodOn(ProviderRestService.class).template()).withRel("createProvider"));
            add(linkTo(methodOn(ProviderRestService.class).findById(id)).withRel("updateProvider"));
            add(linkTo(methodOn(ProviderRestService.class).findById(id)).withRel("deleteProvider"));
        }

        add(linkTo(ProviderRestService.class).withRel("providers"));
    }
}
