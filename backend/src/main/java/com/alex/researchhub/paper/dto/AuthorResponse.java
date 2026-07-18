package com.alex.researchhub.paper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {

    private String authorId;

    private String name;

    private String affiliation;

    private Integer paperCount;

    private Integer citationCount;

    private Integer hIndex;

    private String homepage;

}