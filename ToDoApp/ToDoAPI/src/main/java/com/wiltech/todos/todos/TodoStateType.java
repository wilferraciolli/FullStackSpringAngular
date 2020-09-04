package com.wiltech.todos.todos;

import java.util.stream.Stream;

public enum TodoStateType {

    NEW("New"),
    STARTED("Started"),
    COMPLETED("Completed");

    private final String description;

    TodoStateType(String description) {
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
    public static Stream<TodoStateType> stream() {
        return Stream.of(TodoStateType.values());
    }
}
