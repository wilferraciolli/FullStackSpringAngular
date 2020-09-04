package com.wiltech.todos.providers;

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

/**
 * The type Provider controller.
 */
@CrossOrigin
@RestController
//@ExposesResourceFor(ProviderResource.class)
@RequestMapping("/providers")
public class ProviderRestService extends BaseRestService {

    @Autowired
    private ProviderAppService appService;

    @Autowired
    private ProviderMetaFabricator metaFabricator;

    /**
     * Template response entity.
     * @return the response entity
     */
    @GetMapping("/template")
    public ResponseEntity<ProviderResourceResponse> template() {

        final ProviderResource resource = ProviderResource.builder()
                .build();

        final ProviderResourceResponse response = new ProviderResourceResponse(resource, metaFabricator.createMetaForTemplate());
        response.add(buildSelfLink());

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ProviderResourceResponse> create(@Valid @RequestBody final ProviderResource payload) {
        final ProviderResource createdResource = appService.create(payload);

        return ResponseEntity.created(buildLocationHeader(createdResource.getId()))
                .body(new ProviderResourceResponse(createdResource));
    }

    @GetMapping("")
    public ResponseEntity<ProviderResourceCollectionResponse> findAll() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final List<ProviderResourceResponse> resources = appService.findAll().stream()
                .map(ProviderResourceResponse::new)
                .collect(Collectors.toList());

        final ProviderResourceCollectionResponse response;
        if (resources.size() == 0) {

            response = new ProviderResourceCollectionResponse<>(
                    new CollectionModel<>(emptyResources()),
                    ProviderResourceAssembler.createLinksToCollection(),
                    metaFabricator.createMetaForCollectionResource());
        } else {
            response = new ProviderResourceCollectionResponse<>(
                    new CollectionModel<>(resources),
                    ProviderResourceAssembler.createLinksToCollection(),
                    metaFabricator.createMetaForCollectionResource());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResourceResponse> findById(@PathVariable("id") final Long id) {
        final ProviderResource resource = appService.findById(id);

        return ResponseEntity.ok(new ProviderResourceResponse(resource, metaFabricator.createMetaForSingleResource()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResourceResponse> update(@Valid @PathVariable("id") final Long id,
            @Valid @RequestBody final ProviderResource providerResource) {
        final ProviderResource updatedResource = appService.update(id, providerResource);

        return ResponseEntity.ok(new ProviderResourceResponse(updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") final Long id) {
        appService.deleteById(id);

        return noContent().build();
    }

}
