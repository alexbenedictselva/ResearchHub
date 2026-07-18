package com.alex.researchhub.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alex.researchhub.auth.dto.LoginRequest;
import com.alex.researchhub.auth.dto.LoginResponse;
import com.alex.researchhub.auth.dto.RegisterRequest;
import com.alex.researchhub.auth.mapper.AuthMapper;
import com.alex.researchhub.auth.security.JwtProperties;
import com.alex.researchhub.auth.security.JwtService;
import com.alex.researchhub.common.exception.ResourceAlreadyExistsException;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.repository.ResearcherRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ResearcherRepository researcherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public LoginResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (researcherRepository.existsByEmail(normalizedEmail)) {
            throw new ResourceAlreadyExistsException("A researcher with this email already exists");
        }
        Researcher researcher = AuthMapper.toResearcher(request, passwordEncoder.encode(request.getPassword()));
        Researcher savedResearcher = researcherRepository.save(researcher);
        return tokenResponse(savedResearcher);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword()));
        Researcher researcher = researcherRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Researcher not found"));
        return tokenResponse(researcher);
    }

    private LoginResponse tokenResponse(Researcher researcher) {
        UserDetails userDetails = new com.alex.researchhub.auth.security.CustomUserDetails(researcher);
        return AuthMapper.toLoginResponse(researcher, jwtService.generateAccessToken(userDetails),
                jwtProperties.accessTokenExpirationMs());
    }
}
