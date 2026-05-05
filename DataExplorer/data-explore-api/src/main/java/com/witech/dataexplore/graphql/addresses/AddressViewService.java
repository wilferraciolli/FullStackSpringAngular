package com.witech.dataexplore.graphql.addresses;

import org.springframework.stereotype.Service;

@Service
public class AddressViewService {
    private final AddressViewRepository repository;

    public AddressViewService(AddressViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<AddressView> findAll(AddressFilter filter, AddressSort sort) {
        // Your logic to query the DB View goes here
        return repository.findAll();
    }
}
