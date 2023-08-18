package com.example.onlinelibrary.web.security;

import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class responsible for creating JwtUserDetails instances from User objects.
 */
@Slf4j
public final class JwtUserDetailsCreator {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsCreator.class);


    /**
     * Creates a JwtUserDetails instance based on the provided User object.
     *
     * @param user The User object from which to create the JwtUserDetails.
     * @return A JwtUserDetails instance representing the user's details.
     */
    public static JwtUserDetails create(User user) {
        logger.debug("Creating JwtUserDetails for user: {}", user.getEmail());
        return new JwtUserDetails(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getAuthorities()))
        );
    }

    /**
     * Maps a list of Authority objects to a list of GrantedAuthority objects.
     *
     * @param roles The list of Authority objects to be mapped.
     * @return A list of GrantedAuthority objects representing the user's roles.
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> roles) {
        logger.debug("Mapping user roles to granted authorities.");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

}
