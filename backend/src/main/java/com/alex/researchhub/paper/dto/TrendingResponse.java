package com.alex.researchhub.paper.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrendingResponse {

    private List<PaperResponse> mostCitedPapers;

    private List<AuthorResponse> popularAuthors;

    private List<String> trendingTopics;

}