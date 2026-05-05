package com.witech.dataexplore.graphql.person;

import org.springframework.stereotype.Service;

@Service
public class PersonViewService {
    private final PersonViewRepository repository;

    public PersonViewService(PersonViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<PersonView> findAll(PersonFilter filter, PersonSort sort) {
        // Your logic to query the DB View goes here
        return repository.findAll();
    }
}
