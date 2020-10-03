package com.wiltech.todos.organizer.todos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wiltech.BaseRestService;
import com.wiltech.todos.todos.TodoMetaFabricator;
import com.wiltech.todos.todos.TodoResourceAssembler;
import com.wiltech.todos.todos.TodoResourceCollectionResponse;
import com.wiltech.todos.todos.TodoResourceResponse;

@CrossOrigin
@RestController
@RequestMapping("/organizer/people/{personId}/todos/overview")
public class PersonToDoOverviewRestService extends BaseRestService {

    @Autowired
    private PersonTodoAppService appService;

    @Autowired
    private TodoMetaFabricator metaFabricator;

    @GetMapping("")
    public ResponseEntity<TodoResourceCollectionResponse> findByState(@PathVariable("personId") final Long personId,
            @RequestParam("stateId") final String stateId) {
        final List<TodoResourceResponse> resources = appService.findAllForPersonIdByState(personId, stateId).stream()
                .map(TodoResourceResponse::new)
                .collect(Collectors.toList());

        final TodoResourceCollectionResponse response;
        if (resources.size() == 0) {

            response = new TodoResourceCollectionResponse<>(
                    new CollectionModel<>(emptyResources()),
                    TodoResourceAssembler.createLinksToCollection(personId),
                    metaFabricator.createMetaForCollectionResource(personId));
        } else {
            response = new TodoResourceCollectionResponse<>(
                    new CollectionModel<>(resources),
                    TodoResourceAssembler.createLinksToCollection(personId),
                    metaFabricator.createMetaForCollectionResource(personId));
        }

        return ResponseEntity.ok(response);
    }

}