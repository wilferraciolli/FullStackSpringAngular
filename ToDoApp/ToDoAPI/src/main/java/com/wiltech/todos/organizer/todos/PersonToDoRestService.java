package com.wiltech.todos.organizer.todos;

import static org.springframework.http.ResponseEntity.noContent;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.wiltech.BaseRestService;
import com.wiltech.todos.todos.TodoMetaFabricator;
import com.wiltech.todos.todos.TodoResource;
import com.wiltech.todos.todos.TodoResourceAssembler;
import com.wiltech.todos.todos.TodoResourceCollectionResponse;
import com.wiltech.todos.todos.TodoResourceResponse;

@CrossOrigin
@RestController
@RequestMapping("/organizer/people/{personId}/todos")
public class PersonToDoRestService extends BaseRestService {

    @Autowired
    private PersonTodoAppService appService;

    @Autowired
    private TodoMetaFabricator metaFabricator;

    @GetMapping("/template")
    public ResponseEntity<TodoResourceResponse> template(@PathVariable("personId") final Long personId) {

        final TodoResource resource = appService.template(personId);

        final TodoResourceResponse response = new TodoResourceResponse(resource, metaFabricator.createMetaForTemplate(personId));
        response.add(buildSelfLink());

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<TodoResourceResponse> create(@PathVariable("personId") final Long personId,
            @Valid @RequestBody final TodoResource payload) {
        final TodoResource createdResource = appService.create(personId, payload);

        return ResponseEntity.created(buildLocationHeader(personId, createdResource.getId()))
                .body(new TodoResourceResponse(createdResource, metaFabricator.createMetaForTemplate(personId)));
    }

    @GetMapping("")
    public ResponseEntity<TodoResourceCollectionResponse> findAll(@PathVariable("personId") final Long personId) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final List<TodoResourceResponse> resources = appService.findAllForPersonId(personId).stream()
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

    @GetMapping("/{id}")
    public ResponseEntity<TodoResourceResponse> findById(@PathVariable("personId") final Long personId, @PathVariable("id") final Long id) {
        final TodoResource resource = appService.findById(personId, id);

        return ResponseEntity.ok(new TodoResourceResponse(resource, metaFabricator.createMetaForTemplate(personId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResourceResponse> update(@PathVariable("personId") final Long personId, @Valid @PathVariable("id") final Long id,
            @Valid @RequestBody final TodoResource providerResource) {
        final TodoResource updatedResource = appService.update(personId, id, providerResource);

        return ResponseEntity.ok(new TodoResourceResponse(updatedResource, metaFabricator.createMetaForTemplate(personId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("personId") final Long personId, @PathVariable("id") final Long id) {
        appService.deleteById(personId, id);

        return noContent().build();
    }

    // method to add the location header
    // TODO needs moving to a lib class
    private URI buildLocationHeader(Long personId, Long id) {

        //        return UriComponentsBuilder.fromPath("/organizer/people/{personId}/todos").buildAndExpand(personId, id).toUri();
        return MvcUriComponentsBuilder
                .fromMethodName(this.getClass(), "findById", personId, id)
                .buildAndExpand(personId, id)
                .toUri();

        //  return MvcUriComponentsBuilder.fromController(this.getClass()).path("/{id}").buildAndExpand(new Object[] {id}).toUri();
    }
}
