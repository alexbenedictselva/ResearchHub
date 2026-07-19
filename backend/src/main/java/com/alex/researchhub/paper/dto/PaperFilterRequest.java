package com.alex.researchhub.paper.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperFilterRequest {

    private String query;

    private Integer year;

    private String author;

    private String journal;

    private Integer minCitationCount;

    private Boolean openAccess;

}