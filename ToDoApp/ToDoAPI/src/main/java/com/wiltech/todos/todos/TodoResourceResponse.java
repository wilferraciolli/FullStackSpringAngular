package com.wiltech.todos.todos;

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

@Data
@Builder
@NoArgsConstructor
@Relation(value = "todo", collectionRelation = "collection")
public class TodoResourceResponse extends BaseDTO {

    @JsonProperty("_data")
    private TodoResource data;

    /**
     * Instantiates a new Todo resource response.
     * @param resource the person resource
     */
    public TodoResourceResponse(final TodoResource resource) {
        this.data = resource;
        final Long id = resource.getId();

        addLinks(id);
    }

    public TodoResourceResponse(final TodoResource resource, final Meta meta) {
        this.data = resource;
        super.setMeta(meta);
        final Long id = resource.getId();

        addLinks(id);
    }

    private void addLinks(final Long id) {
        if (Objects.nonNull(id)) {
            add(linkTo(methodOn(TodoRestService.class).findById(id)).withSelfRel());
            add(linkTo(methodOn(TodoRestService.class).findById(id)).withRel("updateTodo"));
            add(linkTo(methodOn(TodoRestService.class).findById(id)).withRel("deleteTodo"));
        }

        add(linkTo(TodoRestService.class).withRel("todos"));
    }
}
