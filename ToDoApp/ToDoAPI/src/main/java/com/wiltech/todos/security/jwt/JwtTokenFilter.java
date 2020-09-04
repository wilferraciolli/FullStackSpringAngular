package com.wiltech.todos.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Jwt token filter.
 * This bean is used to validate a token received on requests.
 */
public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Instantiates a new Jwt token filter.
     *
     * @param jwtTokenProvider the jwt token providers
     */
    public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
            throws IOException, ServletException {
        final String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                final Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (final Exception e) {
            log.error("Bad Token, returning 401 "+ e.getMessage());
            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        }

        //Carry on filtering
        filterChain.doFilter(req, res);
    }
}
