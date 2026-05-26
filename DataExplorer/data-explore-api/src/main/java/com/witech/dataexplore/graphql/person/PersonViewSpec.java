package com.witech.dataexplore.graphql.person;

import org.springframework.data.jpa.domain.Specification;

import com.witech.dataexplore.graphql.helpers.SpecificationHelper;

/**
 * Builds a JPA Specification (= SQL WHERE clause) from a PersonFilter.
 *
 * Each field in the filter maps to one condition on the database column.
 * Null filters are silently skipped — no restriction added for that column.
 *
 * The final Specification is passed to repository.findAll(spec, sort)
 * which generates a single parameterised SQL query — nothing is loaded
 * into Java memory until it already matches all the conditions.
 */
public class PersonViewSpec {

    private PersonViewSpec() {}

    public static Specification<PersonView> fromFilter(PersonFilter filter) {
        // Start with a no-op base (1=1) to avoid calling the ambiguous
        // Specification.where() static factory that Spring Data 4 overloaded
        // with PredicateSpecification.
        Specification<PersonView> base = (root, query, cb) -> cb.conjunction();

        if (filter == null) return base;

        return base
                .and(SpecificationHelper.uuidStringFilter("id",           filter.getId()))
                .and(SpecificationHelper.stringFilter("firstName",        filter.getFirstName()))
                .and(SpecificationHelper.stringFilter("lastName",         filter.getLastName()))
                .and(SpecificationHelper.stringFilter("email",            filter.getEmail()))
                .and(SpecificationHelper.stringFilter("jobTitle",         filter.getJobTitle()))
                .and(SpecificationHelper.dateFilter("startDate",          filter.getStartDate()))
                .and(SpecificationHelper.dateFilter("effectiveDate",      filter.getEffectiveDate()));
    }
}
