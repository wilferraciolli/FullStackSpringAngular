/*
 * (c) Midland Software Limited 2019
 * Name     : PersonRepository.java
 * Author   : ferraciolliw
 * Date     : 09 Sep 2019
 */
package com.wiltech.todos.people;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Person repository.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
