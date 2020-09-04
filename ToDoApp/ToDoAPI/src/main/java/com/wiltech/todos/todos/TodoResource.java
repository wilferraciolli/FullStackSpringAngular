package com.wiltech.todos.todos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonTypeName("todo")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class TodoResource implements Serializable {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private LocalDateTime createdDateTime;

    private LocalDateTime completedDateTime;

    @Enumerated(EnumType.STRING)
    private TodoStateType stateId;

    private Double completionStats;

    private Boolean enabled;

}
