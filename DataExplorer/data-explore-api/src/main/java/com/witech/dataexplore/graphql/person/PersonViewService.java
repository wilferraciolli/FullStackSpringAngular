package com.witech.dataexplore.graphql.person;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.SortOrder;
import com.witech.dataexplore.graphql.helpers.FilterHelper;

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
                .filter(p -> FilterHelper.matchesString(filter.getId(),        p.getId() != null ? p.getId().toString() : null))
                .filter(p -> FilterHelper.matchesString(filter.getFirstName(), p.getFirstName()))
                .filter(p -> FilterHelper.matchesString(filter.getLastName(),  p.getLastName()))
                .filter(p -> FilterHelper.matchesString(filter.getEmail(),     p.getEmail()))
                .filter(p -> FilterHelper.matchesString(filter.getJobTitle(),  p.getJobTitle()))
                .filter(p -> FilterHelper.matchesDate(filter.getStartDate(),     p.getStartDate()))
                .filter(p -> FilterHelper.matchesDate(filter.getEffectiveDate(), p.getEffectiveDate()))
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
