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
public class PaperResponse {

    private String paperId;

    private String title;

    private String abstractText;

    private Integer year;

    private Integer citationCount;

    private String doi;

    private String url;

    private List<AuthorDto> authors;

}