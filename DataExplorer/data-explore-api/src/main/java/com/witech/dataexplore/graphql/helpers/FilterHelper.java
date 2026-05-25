package com.witech.dataexplore.graphql.helpers;

import java.time.LocalDate;

public class FilterHelper {
    private FilterHelper() {
    }

    /**
     * Returns true if the value passes the string filter.
     * If the filter field is blank/null, it is ignored (no filtering).
     * Current behaviour: case-insensitive "contains" check.
     */
    public static boolean matchesString(StringFilter filter, String actual) {
        if (filter == null) {
            return true;
        }

        // isNull check takes priority
        if (filter.getIsNull() != null) {
            return filter.getIsNull() ? actual == null : actual != null;
        }

        if (actual == null) {
            return false; // value is null but no isNull filter — exclude it
        }

        if (filter.getEquals() != null) {
            return actual.equalsIgnoreCase(filter.getEquals());
        }

        if (filter.getNotEquals() != null) {
            return !actual.equalsIgnoreCase(filter.getNotEquals());
        }

        if (filter.getContains() != null) {
            return actual.toLowerCase().contains(filter.getContains().toLowerCase());
        }

        if (filter.getStartsWith() != null) {
            return actual.toLowerCase().startsWith(filter.getStartsWith().toLowerCase());
        }

        return true;
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

    public static boolean matchesDate(DateFilter filter, LocalDate actual) {
        if (filter == null) return true;

        if (filter.getIsNull() != null) {
            return filter.getIsNull() ? actual == null : actual != null;
        }

        if (actual == null) return false;

        if (filter.getEquals() != null)
            return actual.isEqual(LocalDate.parse(filter.getEquals()));

        // Inclusive range: after = start date (>=), before = end date (<=)
        boolean afterOk  = filter.getAfter()  == null || !actual.isBefore(LocalDate.parse(filter.getAfter()));
        boolean beforeOk = filter.getBefore() == null || !actual.isAfter(LocalDate.parse(filter.getBefore()));
        return afterOk && beforeOk;
    }
}
