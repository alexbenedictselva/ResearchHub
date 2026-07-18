package com.alex.researchhub.paper.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoiRequest {

    private String doi;

    private Long researcherId;

    private Long projectId;

}