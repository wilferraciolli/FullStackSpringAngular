package com.witech.dataexplore.graphql.helpers;

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
}
