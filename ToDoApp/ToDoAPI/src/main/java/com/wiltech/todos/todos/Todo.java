package com.wiltech.todos.todos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdDateTime;

    private LocalDateTime completedDateTime;

    @Enumerated(EnumType.STRING)
    private TodoStateType stateId;

    private Double completionStats;

    private Boolean enabled;

    public void update(TodoResource todoResource) {
        this.name = todoResource.getName();
        this.description = todoResource.getDescription();
        this.enabled = todoResource.getEnabled();
    }
}
