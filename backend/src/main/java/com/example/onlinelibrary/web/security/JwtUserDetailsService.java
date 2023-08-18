package com.example.onlinelibrary.web.security;

import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService interface.
 * This service is responsible for loading user details from the database and creating JwtUserDetails objects.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final UserService userService;

    /**
     * Loads user details from the database based on the provided username.
     *
     * @param username The username of the user whose details need to be loaded.
     * @return A UserDetails object representing the user's details.
     * @throws UsernameNotFoundException If the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user details for username: {}", username);
        User user = userService.getByUsername(username);
        logger.debug("User details loaded for username: {}", username);
        return JwtUserDetailsCreator.create(user);
    }

}
