package com.witech.dataexplore.graphql.person;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.witech.dataexplore.graphql.SortOrder;

@Service
public class PersonViewService {
    private final PersonViewRepository repository;

    public PersonViewService(PersonViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<PersonView> findAll(PersonFilter filter, PersonSort sort) {
        Sort springSort = getSorting(sort);
        List<PersonView> results = repository.findAll(springSort);

        if (filter != null) {
            results = filterResults(filter, results);
        }

        return results;
    }

    private List<PersonView> filterResults(PersonFilter filter, List<PersonView> results) {
        return results.stream()
                .filter(p -> !StringUtils.hasText(filter.getFirstName()) || p.getFirstName().toLowerCase().contains(filter.getFirstName().toLowerCase()))
                .filter(p -> !StringUtils.hasText(filter.getLastName()) || p.getLastName().toLowerCase().contains(filter.getLastName().toLowerCase()))
                .filter(p -> !StringUtils.hasText(filter.getEmail()) || p.getEmail().toLowerCase().contains(filter.getEmail().toLowerCase()))
                .filter(p -> !StringUtils.hasText(filter.getJobTitle()) || p.getJobTitle().toLowerCase().contains(filter.getJobTitle().toLowerCase()))
                .toList();
    }

    private Sort getSorting(PersonSort sort) {
        Sort springSort = Sort.unsorted();

        if (sort != null && sort.getField() != null) {
            Sort.Direction direction = (sort.getOrder() == SortOrder.DESC
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC);
            springSort = Sort.by(direction, sort.getField().name());
        }

        return springSort;
    }
}
