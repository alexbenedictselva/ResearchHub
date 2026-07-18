package com.alex.researchhub.dto.publication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublicationRequest {

    private Long id;

    private Long researcher;

    private Long project;

    @NotBlank
    private String title;

    private String abstractText;

    private String doi;

    private String journal;

    private String conference;

    private Integer publicationYear;

    private String pdfUrl;
}
