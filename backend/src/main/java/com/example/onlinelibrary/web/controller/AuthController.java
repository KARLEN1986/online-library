package com.example.onlinelibrary.web.controller;

import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.AuthService;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.dto.auth.JwtRequestDto;
import com.example.onlinelibrary.web.dto.auth.JwtResponseDto;
import com.example.onlinelibrary.web.dto.user.UserDto;
import com.example.onlinelibrary.web.dto.validation.OnCreate;
import com.example.onlinelibrary.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for authentication-related operations.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    /**
     * Handles user login.
     *
     * @param loginRequest The {@link JwtRequestDto} containing login credentials.
     * @return A {@link JwtResponseDto} containing JWT tokens.
     */
    @PostMapping("/login")
    public JwtResponseDto login(@Validated @RequestBody JwtRequestDto loginRequest) {
        logger.info("Received login request for user: {}", loginRequest.getUsername());
        return authService.login(loginRequest);
    }

    /**
     * Handles user registration.
     *
     * @param userDto The {@link UserDto} containing user registration details.
     * @return A {@link UserDto} representing the registered user.
     */
    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        logger.info("Received user registration request for user: {}", userDto.getEmail());
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        logger.info("User registered successfully: {}", createdUser.getEmail());
        return userMapper.toDto(createdUser);
    }

    /**
     * Handles token refreshing.
     *
     * @param refreshToken The refresh token.
     * @return A {@link JwtResponseDto} containing new JWT tokens.
     */
    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody String refreshToken) {
        logger.info("Received token refresh request");
        return authService.refresh(refreshToken);
    }

}
