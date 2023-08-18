package com.example.onlinelibrary.web.security;

import com.example.onlinelibrary.domain.enums.AuthorityName;
import com.example.onlinelibrary.domain.exception.AccessDeniedException;
import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.dto.auth.JwtResponseDto;
import com.example.onlinelibrary.web.security.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Provider for creating, validating, and managing JWT tokens.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Key key;

    /**
     * Initializes the JwtTokenProvider by creating a cryptographic key.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /**
     * Creates an access token for the specified user.
     *
     * @param userId   The user's ID.
     * @param username The user's username.
     * @param roles    The user's roles.
     * @return The access token.
     */
    public String createAccessToken(Long userId, String username, Set<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("roles", resolveRoles(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        logger.debug("Creating access token for user: {}", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Resolves the authority names from a set of user roles.
     *
     * @param userRoles The set of authorities assigned to the user.
     * @return A list of authority names.
     */
    private List<AuthorityName> resolveRoles(Set<Authority> userRoles) {
        List<AuthorityName> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }

    /**
     * Creates a refresh token for the specified user.
     *
     * @param userId   The ID of the user.
     * @param username The username of the user.
     * @return The created refresh token.
     */
    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());

        logger.debug("Creating refresh token for user: {}", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Refreshes the user's access and refresh tokens.
     *
     * @param refreshToken The refresh token.
     * @return The response DTO containing updated tokens.
     * @throws AccessDeniedException If the provided refresh token is invalid.
     */
    public JwtResponseDto refreshUserTokens(String refreshToken) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();

        logger.debug("Refreshing user tokens.");

        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponseDto.setId(userId);
        jwtResponseDto.setUsername(user.getEmail());
        jwtResponseDto.setAccessToken(createAccessToken(userId, user.getEmail(), user.getAuthorities()));
        jwtResponseDto.setRefreshToken(createRefreshToken(userId, user.getEmail()));

        logger.debug("Tokens refreshed for user: {}", user.getEmail());

        return jwtResponseDto;
    }

    /**
     * Validates the provided token's expiration date.
     *
     * @param token The token to validate.
     * @return True if the token is valid, false if it's expired.
     */
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    /**
     * Retrieves the user ID from the specified token.
     *
     * @param token The token from which to extract the user ID.
     * @return The user ID as a string.
     */
    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    /**
     * Retrieves the username from the specified token.
     *
     * @param token The token from which to extract the username.
     * @return The username.
     */
    private String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Retrieves the user's authentication object from the provided token.
     *
     * @param token The token from which to obtain user authentication.
     * @return The user's authentication object.
     */
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
