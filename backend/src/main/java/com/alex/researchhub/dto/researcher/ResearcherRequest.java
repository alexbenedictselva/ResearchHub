package com.alex.researchhub.dto.researcher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResearcherRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    private String password;

    private String university;

    private String department;

    private String country;

    private String specialization;

    private String bio;
}