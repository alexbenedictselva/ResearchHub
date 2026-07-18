package com.alex.researchhub.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final Long researcherId;
    private final String fullName;
    private final String email;
    private final String accessToken;
    private final String tokenType;
    private final long expiresInMs;
}
