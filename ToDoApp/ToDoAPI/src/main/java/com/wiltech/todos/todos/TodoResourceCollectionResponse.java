package com.wiltech.todos.todos;

import org.springframework.hateoas.Link;

import com.wiltech.CollectionResources;
import com.wiltech.Meta;

import lombok.Data;

@Data
public class TodoResourceCollectionResponse<T> extends CollectionResources {

    /**
     * Instantiates a new Todo resource response.
     * @param meta the person resource
     */
    public TodoResourceCollectionResponse(final Iterable<T> content, final Iterable<Link> links, final Meta meta) {
        super(content, links, meta);
    }

}
