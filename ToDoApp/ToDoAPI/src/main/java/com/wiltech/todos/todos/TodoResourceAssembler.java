package com.wiltech.todos.todos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class TodoResourceAssembler {

    public Todo convertToEntity(final TodoResource payload) {

        // used when creating a new todo
        return Todo.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .createdDateTime(LocalDateTime.now())
                .completedDateTime(payload.getCompletedDateTime())
                .stateId(payload.getStateId())
                .completionStats(payload.getCompletionStats())
                .enabled(payload.getEnabled())
                .build();
    }

    public TodoResource transpose(final Todo entity) {
        return TodoResource.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdDateTime(entity.getCreatedDateTime())
                .completedDateTime(entity.getCompletedDateTime())
                .stateId(entity.getStateId())
                .completionStats(entity.getCompletionStats())
                .enabled(entity.getEnabled())
                .build();
    }

    public static List<Link> createLinksToCollection() {
        //add self link to the list
        final Link selfRel = linkTo(methodOn(TodoRestService.class)
                .findAll()).withSelfRel();

        //add create/tempalte link to the list
        final Link createLink = linkTo(methodOn(TodoRestService.class)
                .template()).withRel("createTodo");

        return Arrays.asList(selfRel, createLink);
    }
}
