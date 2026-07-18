package com.alex.researchhub.auth.mapper;

import com.alex.researchhub.auth.dto.LoginResponse;
import com.alex.researchhub.auth.dto.RegisterRequest;
import com.alex.researchhub.entity.Researcher;

public final class AuthMapper {
    private AuthMapper() { }
    public static Researcher toResearcher(RegisterRequest request, String encodedPassword) {
        return Researcher.builder().fullName(request.getFullName()).email(request.getEmail().trim().toLowerCase())
                .password(encodedPassword).build();
    }
    public static LoginResponse toLoginResponse(Researcher researcher, String accessToken, long expiresInMs) {
        return LoginResponse.builder().researcherId(researcher.getId()).fullName(researcher.getFullName())
                .email(researcher.getEmail()).accessToken(accessToken).tokenType("Bearer")
                .expiresInMs(expiresInMs).build();
    }
}
