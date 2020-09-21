package com.wiltech.todos.todos;

import static org.springframework.http.ResponseEntity.noContent;

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

import com.wiltech.BaseRestService;

@CrossOrigin
@RestController
//@ExposesResourceFor(TodoResource.class)
@RequestMapping("/todos")
public class TodoRestService extends BaseRestService {

    @Autowired
    private TodoAppService appService;

    @Autowired
    private TodoMetaFabricator metaFabricator;

    /**
     * Template response entity.
     * @return the response entity
     */
    @GetMapping("/template")
    public ResponseEntity<TodoResourceResponse> template() {

        final TodoResource resource = appService.template();

        final TodoResourceResponse response = new TodoResourceResponse(resource, metaFabricator.createMetaForTemplate());
        response.add(buildSelfLink());

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<TodoResourceResponse> create(@Valid @RequestBody final TodoResource payload) {
        final TodoResource createdResource = appService.create(payload);

        return ResponseEntity.created(buildLocationHeader(createdResource.getId()))
                .body(new TodoResourceResponse(createdResource));
    }

    @GetMapping("")
    public ResponseEntity<TodoResourceCollectionResponse> findAll() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final List<TodoResourceResponse> resources = appService.findAll().stream()
                .map(TodoResourceResponse::new)
                .collect(Collectors.toList());

        final TodoResourceCollectionResponse response;
        if (resources.size() == 0) {

            response = new TodoResourceCollectionResponse<>(
                    new CollectionModel<>(emptyResources()),
                    TodoResourceAssembler.createLinksToCollection(),
                    metaFabricator.createMetaForCollectionResource());
        } else {
            response = new TodoResourceCollectionResponse<>(
                    new CollectionModel<>(resources),
                    TodoResourceAssembler.createLinksToCollection(),
                    metaFabricator.createMetaForCollectionResource());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResourceResponse> findById(@PathVariable("id") final Long id) {
        final TodoResource resource = appService.findById(id);

        return ResponseEntity.ok(new TodoResourceResponse(resource, metaFabricator.createMetaForSingleResource()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResourceResponse> update(@Valid @PathVariable("id") final Long id,
            @Valid @RequestBody final TodoResource providerResource) {
        final TodoResource updatedResource = appService.update(id, providerResource);

        return ResponseEntity.ok(new TodoResourceResponse(updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") final Long id) {
        appService.deleteById(id);

        return noContent().build();
    }

}
