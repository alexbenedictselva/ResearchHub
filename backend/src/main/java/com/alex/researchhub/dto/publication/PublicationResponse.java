package com.alex.researchhub.dto.publication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationResponse {

    private Long id;

    private Long researcher;

    private Long project;

    private String title;

    private String abstractText;

    private String doi;

    private String journal;

    private String conference;

    private Integer publicationYear;

    private String pdfUrl;
}
