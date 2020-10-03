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
//@NamedQuery(name = "Todo.findByPersonIdAndState", query = "SELECT t FROM Todo t WHERE t.personId = :personId AND t.stateId = :stateType")
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personId;

    @Enumerated(EnumType.STRING)
    private TodoType typeId;

    private String name;

    private String description;

    private LocalDateTime createdDateTime;

    private LocalDateTime completedDateTime;

    @Enumerated(EnumType.STRING)
    private TodoStateType stateId;

    private Double completionStats;

    private Boolean enabled;

    public void update(final TodoResource todoResource) {
        this.name = todoResource.getName();
        this.description = todoResource.getDescription();
        this.completedDateTime = todoResource.getCompletedDateTime();
        this.stateId = todoResource.getStateId();
        this.completionStats = todoResource.getCompletionStats();
        this.enabled = todoResource.getEnabled();
    }
}
