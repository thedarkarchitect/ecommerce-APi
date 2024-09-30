package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtAccessToken;
    private String jwtRefreshToken;
    private Long userId;
    private String userRole;
}
