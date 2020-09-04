package com.wiltech.todos.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Provider app service.
 */
@Service
public class ProviderAppService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderResourceAssembler providerResourceAssembler;

    /**
     * Find all list.
     * @return the list
     */
    public List<ProviderResource> findAll() {
        final List<Provider> providers = providerRepository.findAll();

        return providers.stream()
                .map(providerResourceAssembler::transpose)
                .collect(Collectors.toList());
    }

    /**
     * Create provider resource.
     * @param payload the payload
     * @return the provider resource
     */
    public ProviderResource create(final ProviderResource payload) {

        final Provider provider = providerRepository.save(providerResourceAssembler.convertToEntity(payload));
        return providerResourceAssembler.transpose(provider);
    }

    /**
     * Find by id provider resource.
     * @param id the id
     * @return the provider resource
     */
    public ProviderResource findById(final Long id) {
        final Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException());

        return providerResourceAssembler.transpose(provider);
    }

    /**
     * Update provider resource.
     * @param id the id
     * @param providerResource the provider resource
     * @return the provider resource
     */
    public ProviderResource update(final Long id, final ProviderResource providerResource) {
        final Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException());

        provider.update(providerResource);
        providerRepository.save(provider);

        return providerResourceAssembler.transpose(provider);
    }

    /**
     * Delete by id.
     * @param id the id
     */
    public void deleteById(final Long id) {
        final Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException());

        providerRepository.delete(provider);
    }

}
