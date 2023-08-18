package com.example.onlinelibrary.web.security;

import com.example.onlinelibrary.domain.exception.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * Filter to validate JWT tokens and set the authentication context.
 */
@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Filters the request and response to validate the JWT token and set the authentication context.
     *
     * @param servletRequest  The HTTP servlet request.
     * @param servletResponse The HTTP servlet response.
     * @param filterChain     The filter chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-related error occurs.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }

        logger.debug("Bearer token: {}", bearerToken);

        if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("User authenticated: {}", authentication.getName());
                }
            } catch (ResourceNotFoundException ignored) {
                logger.debug("ResourceNotFoundException caught during authentication.");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
