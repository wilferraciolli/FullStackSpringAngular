package com.wiltech.todos.people;

import java.util.stream.Stream;

/**
 * The enum Person gender type.
 */
public enum PersonGenderType {

    /**
     * Male person gender type.
     */
    MALE("Male"),
    /**
     * Female person gender type.
     */
    FEMALE("Female"),

    /**
     * The Prefer not to say.
     */
    PREFER_NOT_TO_SAY("Prefer not to say"),
    /**
     * Other person gender type.
     */
    OTHER("Other");

    private String description;

    PersonGenderType(final String description) {
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
    public static Stream<PersonGenderType> stream() {
        return Stream.of(PersonGenderType.values());
    }

}
