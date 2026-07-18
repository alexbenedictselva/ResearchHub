package com.alex.researchhub.dto.researcher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResearcherResponse {

    private Long id;

    private String fullName;

    private String email;

    private String university;

    private String department;

    private String country;

    private String specialization;

    private String bio;
}