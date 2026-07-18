package com.alex.researchhub.paper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperDetailsResponse {

    private String paperId;

    private String title;

    private String abstractText;

    private Integer year;

    private Integer citationCount;

    private Integer referenceCount;

    private String doi;

    private String venue;

    private String journal;

    private String publicationDate;

    private String url;

    private List<AuthorDto> authors;

    private List<ReferenceDto> references;

    private List<CitationDto> citations;

    private List<SimilarPaperDto> similarPapers;

}