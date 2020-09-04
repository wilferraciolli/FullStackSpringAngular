package com.wiltech.todos.security.jwt.refresh;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to create and manage refresh tokens.
 */
@Service
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String generateRefreshToken(final String userName) {

        final RefreshToken refreshToken = refreshTokenRepository.findByUsername(userName)
                .map(token -> updateRefreshToken(token))
                .orElseGet(() -> createRefreshToken(userName));

        return refreshToken.getRefreshToken();

    }

    private RefreshToken updateRefreshToken(final RefreshToken token) {
        token.updateToken();
        refreshTokenRepository.save(token);

        return token;
    }

    private RefreshToken createRefreshToken(final String userName) {

        return refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .username(userName)
                .createdDate(Instant.now())
                .build());
    }

    void validateRefreshToken(final String token) throws RefreshTokenException {

        refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RefreshTokenException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(final String token) {
        refreshTokenRepository.deleteByRefreshToken(token);
    }

    public RefreshToken loadRefreshToken(final String refreshToken) throws RefreshTokenException {

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException("Could not load refresh token " + refreshToken));
    }
}
