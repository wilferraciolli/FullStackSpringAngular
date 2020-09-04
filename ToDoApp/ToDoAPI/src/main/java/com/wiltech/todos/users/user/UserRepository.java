package com.wiltech.todos.users.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 * This repository is used by Spring framework to resolve users and roles.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by username optional.
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Check username exists boolean.
     * @param username the username
     * @return the boolean
     */
    Long checkUsernameExists(String username);
}
