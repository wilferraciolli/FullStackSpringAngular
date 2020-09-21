package com.wiltech.todos.todos;

import java.util.stream.Stream;

public enum TodoType {
    PERSONAL("Personal"),
    WORK("Work");

    private final String description;

    TodoType(String description) {
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
    public static Stream<TodoType> stream() {
        return Stream.of(TodoType.values());
    }
}