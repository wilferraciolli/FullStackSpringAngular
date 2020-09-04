package com.wiltech.todos.people;

import static org.springframework.http.ResponseEntity.noContent;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiltech.BaseRestService;

/**
 * The type Person rest controller.
 */
@RestController
//@ExposesResourceFor(PersonResource.class)
@RequestMapping(value = "/people", produces = "application/json")
public class PersonRestService extends BaseRestService {

    @Autowired
    private PersonAppService appService;

    @Autowired
    private PersonMetaFabricator metaFabricator;

    /**
     * Template response entity.
     * @return the response entity
     */
    @GetMapping("/template")
    public ResponseEntity<PersonResourceResponse> template() {

        final PersonResource resource = PersonResource.builder()
                .build();

        final PersonResourceResponse response = new PersonResourceResponse(resource, metaFabricator.createMetaForTemplate());
        response.add(buildSelfLink());

        return ResponseEntity.ok(response);
    }

    /**
     * Create response entity.
     * @param payload the person from request
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<PersonResourceResponse> create(@RequestBody @Valid final PersonResource payload) {
        final PersonResource createdResource = appService.create(payload);

        return ResponseEntity.created(buildLocationHeader(createdResource.getId()))
                .body(new PersonResourceResponse(createdResource));
    }

    /**
     * Find all response entity.
     * @return the response entity
     */
    @GetMapping("")
    public ResponseEntity<PersonResourceCollectionResponse<PersonResourceResponse>> findAll() {

        final List<PersonResourceResponse> resources = appService.findAll().stream()
                .map(PersonResourceResponse::new)
                .collect(Collectors.toList());

        final PersonResourceCollectionResponse<PersonResourceResponse> response = new PersonResourceCollectionResponse<>(
                new CollectionModel(resources),
                PersonResourceAssembler.createLinksToCollection(),
                metaFabricator.createMetaForCollectionResource());

        return ResponseEntity.ok(response);
    }

    /**
     * Find by id response entity.
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonResourceResponse> findById(@PathVariable final long id) {

        final PersonResource resource = appService.findById(id);

        return ResponseEntity.ok(new PersonResourceResponse(resource, metaFabricator.createMetaForSingleResource()));
    }

    /**
     * Update response entity.
     * @param id the id
     * @param personFromRequest the person from request
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonResourceResponse> update(@PathVariable("id") final long id,
            @RequestBody @Valid final PersonResource personFromRequest) {
        final PersonResource updatedResource = appService.update(id, personFromRequest);

        return ResponseEntity.ok(new PersonResourceResponse(updatedResource));
    }

    /**
     * Delete response entity.
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final long id) {
        appService.deleteById(id);

        return noContent().build();
    }
}
