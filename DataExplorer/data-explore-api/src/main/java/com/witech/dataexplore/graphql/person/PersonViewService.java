package com.witech.dataexplore.graphql.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.PageInput;
import com.witech.dataexplore.graphql.SortOrder;

@Service
public class PersonViewService {
    private final PersonViewRepository repository;

    public PersonViewService(PersonViewRepository repository) {
        this.repository = repository;
    }

    public PersonPage findAll(PersonFilter filter, PersonSort sort, PageInput pageInput) {
        // PageRequest combines LIMIT + OFFSET + ORDER BY into one query.
        // The database never loads more than 'size' rows per request.
        PageRequest pageable = PageRequest.of(
                pageInput == null ? 0    : pageInput.getPage(),
                pageInput == null ? 20   : pageInput.getSize(),
                getSorting(sort));

        Specification<PersonView> spec = PersonViewSpec.fromFilter(filter);
        Page<PersonView> result = repository.findAll(spec, pageable);

        return new PersonPage(
                result.getContent(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.getNumber(),
                result.getSize());
    }

    private Sort getSorting(PersonSort sort) {
        if (sort != null && sort.getField() != null) {
            Sort.Direction dir = sort.getOrder() == SortOrder.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            return Sort.by(dir, sort.getField().name());
        }
        return Sort.unsorted();
    }
}
