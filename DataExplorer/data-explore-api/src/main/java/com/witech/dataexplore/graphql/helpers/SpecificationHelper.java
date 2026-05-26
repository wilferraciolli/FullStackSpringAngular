package com.witech.dataexplore.graphql.helpers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

/**
 * Converts our GraphQL filter objects (StringFilter, DateFilter) into
 * JPA Specification predicates. Each method ALWAYS returns a non-null
 * Specification — cb.conjunction() ("1=1") when there is nothing to filter,
 * which the SQL optimiser removes.  This avoids null-overload ambiguity
 * introduced in Spring Data 4 with the new PredicateSpecification API.
 */
public class SpecificationHelper {

    private SpecificationHelper() {}

    /**
     * Translates a StringFilter into a SQL predicate for a String column.
     * e.g. contains("smith") → WHERE LOWER(first_name) LIKE '%smith%'
     */
    public static <T> Specification<T> stringFilter(String fieldName, StringFilter filter) {
        return (root, query, cb) -> {
            if (filter == null) return cb.conjunction();
            // IS NULL / IS NOT NULL check
            if (filter.getIsNull() != null) {
                return filter.getIsNull()
                        ? root.get(fieldName).isNull()
                        : root.get(fieldName).isNotNull();
            }
            // Wrap field in LOWER() so all comparisons are case-insensitive
            Expression<String> path = cb.lower(root.get(fieldName));
            if (filter.getEquals() != null)
                return cb.equal(path, filter.getEquals().toLowerCase());
            if (filter.getNotEquals() != null)
                return cb.notEqual(path, filter.getNotEquals().toLowerCase());
            // LIKE '%value%'
            if (filter.getContains() != null)
                return cb.like(path, "%" + filter.getContains().toLowerCase() + "%");
            // LIKE 'value%'
            if (filter.getStartsWith() != null)
                return cb.like(path, filter.getStartsWith().toLowerCase() + "%");
            return cb.conjunction(); // filter present but no condition set — no restriction
        };
    }

    /**
     * Like stringFilter but CAST(field AS VARCHAR) first — required for UUID columns.
     * e.g. contains("550e") → WHERE LOWER(CAST(id AS VARCHAR)) LIKE '%550e%'
     */
    public static <T> Specification<T> uuidStringFilter(String fieldName, StringFilter filter) {
        return (root, query, cb) -> {
            if (filter == null) return cb.conjunction();
            if (filter.getIsNull() != null)
                return filter.getIsNull() ? root.get(fieldName).isNull() : root.get(fieldName).isNotNull();

            // Cast UUID → VARCHAR, then LOWER for case-insensitive match
            Expression<String> path = cb.lower(root.get(fieldName).as(String.class));
            if (filter.getEquals() != null)    return cb.equal(path, filter.getEquals().toLowerCase());
            if (filter.getNotEquals() != null) return cb.notEqual(path, filter.getNotEquals().toLowerCase());
            if (filter.getContains() != null)  return cb.like(path, "%" + filter.getContains().toLowerCase() + "%");
            if (filter.getStartsWith() != null) return cb.like(path, filter.getStartsWith().toLowerCase() + "%");
            return cb.conjunction();
        };
    }

    /**
     * Translates a DateFilter into a SQL predicate for a LocalDate column.
     * after and before can both be set together to express an inclusive range:
     *   WHERE effective_date >= '2026-01-01' AND effective_date <= '2026-12-31'
     */
    public static <T> Specification<T> dateFilter(String fieldName, DateFilter filter) {
        return (root, query, cb) -> {
            if (filter == null) return cb.conjunction();
            if (filter.getIsNull() != null)
                return filter.getIsNull() ? root.get(fieldName).isNull() : root.get(fieldName).isNotNull();

            Expression<LocalDate> path = root.get(fieldName);
            if (filter.getEquals() != null) return cb.equal(path, LocalDate.parse(filter.getEquals()));

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getAfter() != null)
                predicates.add(cb.greaterThanOrEqualTo(path, LocalDate.parse(filter.getAfter())));
            if (filter.getBefore() != null)
                predicates.add(cb.lessThanOrEqualTo(path, LocalDate.parse(filter.getBefore())));
            if (!predicates.isEmpty())
                return cb.and(predicates.toArray(new Predicate[0]));

            return cb.conjunction();
        };
    }
}
