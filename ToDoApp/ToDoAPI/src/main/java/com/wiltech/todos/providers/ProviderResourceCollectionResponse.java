package com.wiltech.todos.providers;

import org.springframework.hateoas.Link;

import com.wiltech.CollectionResources;
import com.wiltech.Meta;

import lombok.Data;

/**
 * The type Provider resource collection response.
 * @param <T> the type parameter
 */
@Data
public class ProviderResourceCollectionResponse<T> extends CollectionResources {

    /**
     * Instantiates a new Provider resource collection response.
     * @param content the content
     * @param links the links
     * @param meta the meta
     */
    public ProviderResourceCollectionResponse(final Iterable<T> content, final Iterable<Link> links, final Meta meta) {
        super(content, links, meta);
    }

}
