package com.alex.researchhub.paper.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaperResponse {
    private String title;
    private String abstractText;
    private String doi;
    private List<AuthorDto> authors;
    private String year;
    private String url;
}
