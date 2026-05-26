package com.witech.dataexplore.graphql.addresses;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.SortOrder;

@Service
public class AddressViewService {
    private final AddressViewRepository repository;

    public AddressViewService(AddressViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<AddressView> findAll(AddressFilter filter, AddressSort sort) {
        Specification<AddressView> spec = AddressViewSpec.fromFilter(filter);
        return repository.findAll(spec, getSorting(sort));
    }

    private Sort getSorting(AddressSort sort) {
        if (sort != null && sort.getField() != null) {
            Sort.Direction direction = sort.getOrder() == SortOrder.DESC
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            return Sort.by(direction, sort.getField().name());
        }
        return Sort.unsorted();
    }
}
