package com.wiltech.todos.users;

import org.springframework.hateoas.Link;

import com.wiltech.CollectionResources;
import com.wiltech.Meta;

import lombok.Data;

/**
 * The type User resource response collection.
 * @param <T> the type parameter
 */
@Data
public class UserResourceResponseCollection<T> extends CollectionResources {

    /**
     * Instantiates a new User resource response collection.
     * @param content the content
     * @param links the links
     * @param meta the meta
     */
    public UserResourceResponseCollection(final Iterable<T> content, final Iterable<Link> links, final Meta meta) {
        super(content, links, meta);
    }

}
