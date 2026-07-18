package com.alex.researchhub.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.researchhub.auth.dto.LoginRequest;
import com.alex.researchhub.auth.dto.LoginResponse;
import com.alex.researchhub.auth.dto.RegisterRequest;
import com.alex.researchhub.auth.service.AuthService;
import com.alex.researchhub.common.response.ApiResponse;
import com.alex.researchhub.common.response.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseBuilder.success("Registration successful", authService.register(request), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseBuilder.success("Login successful", authService.login(request), HttpStatus.OK);
    }
}
