package com.witech.dataexplore.graphql.person;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.SortOrder;

@Service
public class PersonViewService {
    private final PersonViewRepository repository;

    public PersonViewService(PersonViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<PersonView> findAll(PersonFilter filter, PersonSort sort) {
        // Build the WHERE clause from the filter — null fields are silently skipped.
        // Spring Data JPA translates the Specification into a single parameterised
        // SQL query; only rows that already match all conditions come back over JDBC.
        Specification<PersonView> spec = PersonViewSpec.fromFilter(filter);
        return repository.findAll(spec, getSorting(sort));
    }

    private Sort getSorting(PersonSort sort) {
        if (sort != null && sort.getField() != null) {
            Sort.Direction direction = sort.getOrder() == SortOrder.DESC
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            return Sort.by(direction, sort.getField().name());
        }
        return Sort.unsorted();
    }
}
