package com.witech.dataexplore.graphql.addresses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.witech.dataexplore.graphql.PageInput;
import com.witech.dataexplore.graphql.PageResult;
import com.witech.dataexplore.graphql.SortOrder;

@Service
public class AddressViewService {
    private final AddressViewRepository repository;

    public AddressViewService(AddressViewRepository repository) {
        this.repository = repository;
    }

    public PageResult<AddressView> findAll(AddressFilter filter, AddressSort sort, PageInput pageInput) {
        PageRequest pageable = PageRequest.of(
                pageInput == null ? 0  : pageInput.getPage(),
                pageInput == null ? 20 : pageInput.getSize(),
                getSorting(sort));

        Specification<AddressView> spec = AddressViewSpec.fromFilter(filter);
        Page<AddressView> result = repository.findAll(spec, pageable);

        return PageResult.of(result);
    }

    private Sort getSorting(AddressSort sort) {
        if (sort != null && sort.getField() != null) {
            Sort.Direction dir = sort.getOrder() == SortOrder.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            return Sort.by(dir, sort.getField().name());
        }
        return Sort.unsorted();
    }
}
