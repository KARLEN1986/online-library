package com.example.onlinelibrary.web.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for JWT token settings.
 */
@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * Secret key used for signing JWT tokens.
     */
    private String secret;

    /**
     * Expiration time in milliseconds for access JWT tokens.
     */
    private long access;

    /**
     * Expiration time in milliseconds for refresh JWT tokens.
     */
    private long refresh;

}
