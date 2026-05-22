package com.witech.dataexplore.graphql.addresses;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.witech.dataexplore.graphql.SortOrder;
import com.witech.dataexplore.graphql.helpers.FilterHelper;

@Service
public class AddressViewService {
    private final AddressViewRepository repository;

    public AddressViewService(AddressViewRepository repository) {
        this.repository = repository;
    }

    public Iterable<AddressView> findAll(AddressFilter filter, AddressSort sort) {
        Sort springSort = getSorting(sort);
        List<AddressView> results = repository.findAll(springSort);

        if (filter != null) {
            results = filterResults(filter, results);
        }

        return results;
    }

    private List<AddressView> filterResults(AddressFilter filter, List<AddressView> results) {
        return results.stream()
                .filter(a -> FilterHelper.matchesString(filter.getCity(),   a.getCity()))
                .filter(a -> FilterHelper.matchesString(filter.getStreet(), a.getStreet()))
                .toList();
    }

    private Sort getSorting(AddressSort sort) {
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
