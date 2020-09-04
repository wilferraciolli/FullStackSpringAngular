package com.wiltech.todos.people;

import java.util.stream.Stream;

/**
 * The enum Person marital status type.
 */
public enum PersonMaritalStatusType {

    /**
     * Single person marital status type.
     */
    SINGLE("Single"),
    /**
     * Married person marital status type.
     */
    MARRIED("Married"),
    /**
     * Divorced person marital status type.
     */
    DIVORCED("Divorced"),
    /**
     * Widow person marital status type.
     */
    WIDOW("Widow"),
    /**
     * Other person marital status type.
     */
    OTHER("Other");

    private String description;

    PersonMaritalStatusType(final String description) {
        this.description = description;
    }

    /**
     * Gets description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Stream stream.
     * @return the stream
     */
    public static Stream<PersonMaritalStatusType> stream() {
        return Stream.of(PersonMaritalStatusType.values());
    }
}
