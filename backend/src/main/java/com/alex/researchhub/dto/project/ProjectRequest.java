package com.alex.researchhub.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequest {

    private Long id;

    private Long researcher;

    @NotBlank
    private String title;

    private String description;

    private String domain;

    private String status;

    private Double innovationScore;

}
