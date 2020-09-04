/*
 * (c) Midland Software Limited 2020
 * Name     : TodoRepository.java
 * Author   : ferraciolliw
 * Date     : 04 Sep 2020
 */
package com.wiltech.todos.todos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
