package com.example.onlinelibrary.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of UserDetails for representing user details in JWT authentication.
 */
@Data
@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

    /**
     * The user's unique identifier.
     */
    private final Long id;

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The name of the user.
     */
    private final String name;

    /**
     * The hashed password of the user.
     */
    private final String password;

    /**
     * The collection of granted authorities assigned to the user.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return {@code true} if the user's account is not expired, {@code false} otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * @return {@code true} if the user's account is not locked, {@code false} otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are not expired.
     *
     * @return {@code true} if the user's credentials are not expired, {@code false} otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
