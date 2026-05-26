package com.witech.dataexplore.graphql.addresses;

import org.springframework.data.jpa.domain.Specification;

import com.witech.dataexplore.graphql.helpers.SpecificationHelper;

public class AddressViewSpec {

    private AddressViewSpec() {}

    public static Specification<AddressView> fromFilter(AddressFilter filter) {
        Specification<AddressView> base = (root, query, cb) -> cb.conjunction();

        if (filter == null) return base;

        return base
                .and(SpecificationHelper.stringFilter("city",          filter.getCity()))
                .and(SpecificationHelper.stringFilter("street",        filter.getStreet()))
                .and(SpecificationHelper.dateFilter("effectiveDate",   filter.getEffectiveDate()));
    }
}
