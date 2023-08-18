package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.AuthService;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.dto.auth.JwtRequestDto;
import com.example.onlinelibrary.web.dto.auth.JwtResponseDto;
import com.example.onlinelibrary.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of the AuthService interface for authentication and token management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponseDto login(JwtRequestDto loginRequest) {

        logger.info("Processing login request for username: {}", loginRequest.getUsername());

        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        Long userId = user.getId();
        String userEmail = user.getEmail();
        Set<Authority> userAuthorities = user.getAuthorities();
        jwtResponseDto.setId(userId);
        jwtResponseDto.setUsername(userEmail);
        jwtResponseDto.setAccessToken(jwtTokenProvider.createAccessToken(userId, userEmail, userAuthorities));
        jwtResponseDto.setRefreshToken(jwtTokenProvider.createRefreshToken(userId, userEmail));

        logger.info("Login successful for user: {}", loginRequest.getUsername());
        return jwtResponseDto;
    }

    @Override
    public JwtResponseDto refresh(String refreshToken) {
        logger.info("Processing token refresh request");

        JwtResponseDto refreshedTokens = jwtTokenProvider.refreshUserTokens(refreshToken);

        logger.info("Token refresh successful");

        return refreshedTokens;
    }

}
