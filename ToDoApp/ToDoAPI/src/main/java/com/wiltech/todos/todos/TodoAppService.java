package com.wiltech.todos.todos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiltech.todos.exceptions.EntityNotFoundException;

@Service
public class TodoAppService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoResourceAssembler todoResourceAssembler;

    public TodoResource template() {

        return TodoResource.builder()
                .name("Name")
                .typeId(TodoType.PERSONAL)
                .stateId(TodoStateType.NEW)
                .createdDateTime(LocalDateTime.now())
                .completionStats(0D)
                .enabled(true)
                .build();
    }

    /**
     * Find all list.
     * @return the list
     */
    public List<TodoResource> findAll() {
        final List<Todo> todos = todoRepository.findAll();

        return todos.stream()
                .map(todoResourceAssembler::transpose)
                .collect(Collectors.toList());
    }

    /**
     * Create todo resource.
     * @param payload the payload
     * @return the todo resource
     */
    public TodoResource create(final TodoResource payload) {

        final Todo todo = todoRepository.save(todoResourceAssembler.createEntity(payload));
        return todoResourceAssembler.transpose(todo);
    }

    /**
     * Find by id todo resource.
     * @param id the id
     * @return the todo resource
     */
    public TodoResource findById(final Long id) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        return todoResourceAssembler.transpose(todo);
    }

    /**
     * Update todo resource.
     * @param id the id
     * @param todoResource the todo resource
     * @return the todo resource
     */
    public TodoResource update(final Long id, final TodoResource todoResource) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        todo.update(todoResource);
        todoRepository.save(todo);

        return todoResourceAssembler.transpose(todo);
    }

    /**
     * Delete by id.
     * @param id the id
     */
    public void deleteById(final Long id) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find todo by id"));

        todoRepository.delete(todo);
    }
}
