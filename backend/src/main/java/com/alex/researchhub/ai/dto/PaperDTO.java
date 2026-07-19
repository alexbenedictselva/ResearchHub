package com.alex.researchhub.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaperDTO {

    private String paperId;
    private String title;

    @JsonProperty("abstract")
    private String abstractText;

    private Integer year;
    private Integer citationCount;

    public PaperDTO() {}

    public PaperDTO(String paperId, String title, String abstractText, Integer year, Integer citationCount) {
        this.paperId = paperId;
        this.title = title;
        this.abstractText = abstractText;
        this.year = year;
        this.citationCount = citationCount;
    }

    public String getPaperId() { return paperId; }
    public void setPaperId(String paperId) { this.paperId = paperId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getCitationCount() { return citationCount; }
    public void setCitationCount(Integer citationCount) { this.citationCount = citationCount; }
}