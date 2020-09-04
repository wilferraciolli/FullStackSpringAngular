package com.wiltech.todos.security.jwt.refresh;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Refresh token repository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find by refresh token optional.
     * @param refreshToken the refresh token
     * @return the optional
     */
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    /**
     * Find by username optional.
     * @param username the username
     * @return the optional
     */
    Optional<RefreshToken> findByUsername(String username);

    /**
     * Delete by refresh token.
     * @param refreshToken the refresh token
     */
    void deleteByRefreshToken(String refreshToken);
}
