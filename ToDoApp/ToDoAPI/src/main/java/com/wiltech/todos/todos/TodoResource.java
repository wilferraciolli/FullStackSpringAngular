package com.wiltech.todos.todos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private TodoType typeId;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private LocalDateTime createdDateTime;

    private LocalDateTime completedDateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TodoStateType stateId;

    @NotNull
    private Double completionStats;

    @JsonProperty("published")
    private Boolean enabled;

}
