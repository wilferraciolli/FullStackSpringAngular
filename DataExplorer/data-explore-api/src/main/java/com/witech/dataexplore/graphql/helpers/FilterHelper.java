package com.witech.dataexplore.graphql.helpers;

import java.time.LocalDate;

/**
 * In-memory filter helpers — kept as utilities (e.g. for unit tests).
 * Production filtering now uses SpecificationHelper (JPA / SQL level).
 */
public class FilterHelper {
    private FilterHelper() {}

    public static boolean matchesString(StringFilter filter, String actual) {
        if (filter == null) return true;
        if (filter.getIsNull() != null)
            return filter.getIsNull() ? actual == null : actual != null;
        if (actual == null) return false;
        if (filter.getEquals() != null) return actual.equalsIgnoreCase(filter.getEquals());
        if (filter.getNotEquals() != null) return !actual.equalsIgnoreCase(filter.getNotEquals());
        if (filter.getContains() != null) return actual.toLowerCase().contains(filter.getContains().toLowerCase());
        if (filter.getStartsWith() != null) return actual.toLowerCase().startsWith(filter.getStartsWith().toLowerCase());
        return true;
    }

    public static boolean matchesDate(DateFilter filter, LocalDate actual) {
        if (filter == null) return true;
        if (filter.getIsNull() != null)
            return filter.getIsNull() ? actual == null : actual != null;
        if (actual == null) return false;
        if (filter.getEquals() != null) return actual.isEqual(LocalDate.parse(filter.getEquals()));
        // Inclusive range
        boolean afterOk  = filter.getAfter()  == null || !actual.isBefore(LocalDate.parse(filter.getAfter()));
        boolean beforeOk = filter.getBefore() == null || !actual.isAfter(LocalDate.parse(filter.getBefore()));
        return afterOk && beforeOk;
    }
}
