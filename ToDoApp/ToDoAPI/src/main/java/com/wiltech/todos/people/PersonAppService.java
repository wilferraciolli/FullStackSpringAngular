/*
 * (c) Midland Software Limited 2019
 * Name     : PersonAppService.java
 * Author   : ferraciolliw
 * Date     : 09 Sep 2019
 */
package com.wiltech.todos.people;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Person app service.
 */
@Service
public class PersonAppService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonResourceAssembler assembler;

    /**
     * Find all list.
     * @return the list
     */
    public List<PersonResource> findAll() {

        return repository.findAll().stream()
                .map(assembler::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create person resource.
     * @param payload the payload
     * @return the person resource
     */
    public PersonResource create(@Valid final PersonResource payload) {

        final Person Person = repository.save(assembler.convertToEntity(payload));

        return assembler.convertToDTO(Person);
    }

    /**
     * Find by id person resource.
     * @param id the id
     * @return the person resource
     */
    public PersonResource findById(final Long id) {
        final Person person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return assembler.convertToDTO(person);
    }

    /**
     * Update person resource.
     * @param id the id
     * @param PersonResource the person resource
     * @return the person resource
     */
    public PersonResource update(final Long id, @Valid final PersonResource PersonResource) {
        final Person person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        person.updatePerson(PersonResource);
        repository.save(person);

        return assembler.convertToDTO(person);
    }

    /**
     * Delete by id.
     * @param id the id
     */
    public void deleteById(final Long id) {
        final Person person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        repository.delete(person);
    }
}
