package com.example.onlinelibrary.web.dto.auth;

import lombok.Data;

@Data
public class JwtResponseDto {

    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;

}
