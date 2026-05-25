package com.witech.dataexplore.graphql.person;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.SortOrder;
import com.witech.dataexplore.graphql.helpers.DateFilter;
import com.witech.dataexplore.graphql.helpers.FilterHelper;
import com.witech.dataexplore.graphql.helpers.StringFilter;

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
                .filter(p -> FilterHelper.matchesString(filter.getFirstName(), p.getFirstName()))
                .filter(p -> FilterHelper.matchesString(filter.getLastName(),  p.getLastName()))
                .filter(p -> FilterHelper.matchesString(filter.getEmail(),     p.getEmail()))
                .filter(p -> FilterHelper.matchesString(filter.getJobTitle(),  p.getJobTitle()))
                .filter(p -> FilterHelper.matchesDate(filter.getStartDate(),   p.getStartDate()))
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

    private boolean applyString(StringFilter f, String value) {
        if (f == null) {
            return true;
        }
        if (Boolean.TRUE.equals(f.getIsNull())) {
            return value == null;
        }
        if (Boolean.FALSE.equals(f.getIsNull())) {
            return value != null;
        }
        if (f.getEquals() != null) {
            return value != null && value.equalsIgnoreCase(f.getEquals());
        }
        if (f.getNotEquals() != null) {
            return value == null || !value.equalsIgnoreCase(f.getNotEquals());
        }
        if (f.getContains() != null) {
            return value != null && value.toLowerCase().contains(f.getContains().toLowerCase());
        }
        if (f.getStartsWith() != null) {
            return value != null && value.toLowerCase().startsWith(f.getStartsWith().toLowerCase());
        }

        return true;
    }

    private boolean applyDate(DateFilter f, LocalDate value) {
        if (f == null) {
            return true;
        }
        if (Boolean.TRUE.equals(f.getIsNull())) {
            return value == null;
        }
        if (Boolean.FALSE.equals(f.getIsNull())) {
            return value != null;
        }
        LocalDate parsed = f.getEquals() != null ? LocalDate.parse(f.getEquals()) : null;
        if (parsed != null) {
            return value != null && value.isEqual(parsed);
        }
        if (f.getAfter() != null) {
            return value != null && value.isAfter(LocalDate.parse(f.getAfter()));
        }
        if (f.getBefore() != null) {
            return value != null && value.isBefore(LocalDate.parse(f.getBefore()));
        }

        return true;
    }
}
