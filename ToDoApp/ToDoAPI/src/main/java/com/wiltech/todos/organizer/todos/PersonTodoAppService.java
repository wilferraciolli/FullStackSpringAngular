package com.wiltech.todos.organizer.todos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiltech.todos.exceptions.EntityNotFoundException;
import com.wiltech.todos.todos.Todo;
import com.wiltech.todos.todos.TodoRepository;
import com.wiltech.todos.todos.TodoResource;
import com.wiltech.todos.todos.TodoResourceAssembler;
import com.wiltech.todos.todos.TodoStateType;
import com.wiltech.todos.todos.TodoType;

@Service
public class PersonTodoAppService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoResourceAssembler todoResourceAssembler;

    public TodoResource template(final Long personId) {

        return TodoResource.builder()
                .personId(personId)
                .typeId(TodoType.PERSONAL)
                .stateId(TodoStateType.NEW)
                .createdDateTime(LocalDateTime.now())
                .completionStats(0D)
                .enabled(true)
                .build();
    }

    public List<TodoResource> findAllForPersonId(final Long personId) {
        final List<Todo> todos = todoRepository.findAllByPersonIdOrderByCreatedDateTimeAsc(personId);

        return todos.stream()
                .map(todoResourceAssembler::transpose)
                .collect(Collectors.toList());
    }

    public TodoResource create(final Long personId, final TodoResource payload) {
        final Todo todo = todoRepository.save(todoResourceAssembler.createEntity(personId, payload));

        return todoResourceAssembler.transpose(todo);
    }

    public TodoResource findById(final Long personId, final Long id) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        return todoResourceAssembler.transpose(todo);
    }

    public TodoResource update(final Long personId, final Long id, final TodoResource todoResource) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        todo.update(todoResource);
        todoRepository.save(todo);

        return todoResourceAssembler.transpose(todo);
    }

    public void deleteById(final Long personId, final Long id) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        todoRepository.delete(todo);
    }

}
