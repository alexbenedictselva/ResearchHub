package com.alex.researchhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

    private Long id;

    private Long researcher;

    private String title;

    private String description;

    private String domain;

    private String status;

    private Double innovationScore;

}
