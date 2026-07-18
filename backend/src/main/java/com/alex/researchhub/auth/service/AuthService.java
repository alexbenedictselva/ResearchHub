package com.alex.researchhub.auth.service;

import com.alex.researchhub.auth.dto.LoginRequest;
import com.alex.researchhub.auth.dto.LoginResponse;
import com.alex.researchhub.auth.dto.RegisterRequest;

public interface AuthService {
    LoginResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
