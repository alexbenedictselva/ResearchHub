package com.alex.researchhub.paper.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrossrefResponse {

    private String title;

    private List<String> authors;

    private String journal;

    private Integer year;

    private String doi;

}