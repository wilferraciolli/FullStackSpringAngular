package com.wiltech.todos.people;

import org.springframework.hateoas.Link;

import com.wiltech.CollectionResources;
import com.wiltech.Meta;

import lombok.Data;

/**
 * The type Person resource response.
 */
@Data
public class PersonResourceCollectionResponse<T> extends CollectionResources {

    /**
     * Instantiates a new Person resource response.
     * @param meta the person resource
     */
    public PersonResourceCollectionResponse(final Iterable<T> content, final Iterable<Link> links, final Meta meta) {
        super(content, links, meta);
    }

}
