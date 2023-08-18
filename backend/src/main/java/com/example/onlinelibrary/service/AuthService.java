package com.example.onlinelibrary.service;

import com.example.onlinelibrary.domain.exception.AccessDeniedException;
import com.example.onlinelibrary.web.dto.auth.JwtRequestDto;
import com.example.onlinelibrary.web.dto.auth.JwtResponseDto;

/**
 * Service interface for handling authentication and token management.
 */
public interface AuthService {
    /**
     * Performs user authentication and generates access and refresh tokens.
     *
     * @param loginRequest The JwtRequestDto containing login credentials.
     * @return The JwtResponseDto containing access and refresh tokens.
     */
    JwtResponseDto login(JwtRequestDto loginRequest);

    /**
     * Refreshes user tokens using a valid refresh token.
     *
     * @param refreshToken The refresh token to be used for token refresh.
     * @return The JwtResponseDto containing new access and refresh tokens.
     * @throws AccessDeniedException If the refresh token is invalid or expired.
     */
    JwtResponseDto refresh(String refreshToken);

}
