package com.wiltech.todos.users;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * The type User rest service.
 */
@RestController()
//@ExposesResourceFor(UserResource.class)
@RequestMapping(value = "/users", produces = "application/json")
public class UserRestService extends BaseRestService {

    @Autowired
    private UserAppService appService;

    @Autowired
    private UserMetaFabricator metaFabricator;

    /**
     * Template response entity.
     * @return the response entity
     */
    @GetMapping("/template")
    public ResponseEntity<UserResourceResponse> template() {
        final UserResource resource = UserResource.builder()
                .build();

        final UserResourceResponse response = new UserResourceResponse(resource, this.metaFabricator.createMetaForTemplate());
        response.add(this.buildSelfLink());

        return ResponseEntity.ok(response);
    }

    /**
     * Create response entity.
     * @param payload the payload
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<UserResourceResponse> create(@RequestBody @Valid final UserResource payload) {
        final UserResource createdResource = this.appService.create(payload);

        return ResponseEntity.created(this.buildLocationHeader(createdResource.getId()))
                .body(new UserResourceResponse(createdResource));
    }

    /**
     * Find all response entity.
     * @return the response entity
     */
    @GetMapping("")
    public ResponseEntity<UserResourceResponseCollection<UserResourceResponse>> findAll() {

        final List<UserResourceResponse> resources = this.appService.findUsers().stream()
                .map(UserResourceResponse::new)
                .collect(Collectors.toList());

        //get the resource collection and add link to the collection
        final UserResourceResponseCollection<UserResourceResponse> response = new UserResourceResponseCollection<>(
                new CollectionModel<>(resources),
                UserResourceAssembler.createLinksToCollection(),
                this.metaFabricator.createMetaForCollectionResource());

        return ResponseEntity.ok(response);
    }

    /**
     * Find by id response entity.
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResourceResponse> findById(@PathVariable("id") final Long id) {

        final UserResource resource = this.appService.findById(id);

        return ResponseEntity.ok(new UserResourceResponse(resource, this.metaFabricator.createMetaForSingleResource()));
    }

    @GetMapping("/usernames/{username}/availability")
    public ResponseEntity<Boolean> checkUsernameAvailability(@PathVariable("username") final String username) {

        final boolean isAvailable = this.appService.checkUsernameAvailability(StringUtils.defaultString(username));

        return ResponseEntity.ok(isAvailable);
    }

    /**
     * Update response entity.
     * @param id the id
     * @param payload the payload
     * @return the response entity
     */
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or checkOwnerByUserId(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<UserResourceResponse> update(@PathVariable("id") final Long id, @RequestBody @Valid final UserResource payload) {
        final UserResource updatedResource = this.appService.update(id, payload);

        return ResponseEntity.ok(new UserResourceResponse(updatedResource));
    }

    /**
     * Delete by id response entity.
     * @param id the id
     * @return the response entity
     */
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or checkOwnerByUserId(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") final Long id) {
        this.appService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
