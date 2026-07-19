package com.alex.researchhub.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaperDTO {

    private String paperId;

    private String title;

    @JsonProperty("abstract")
    private String abstractText;

    public PaperDTO() {
    }

    public PaperDTO(String paperId, String title, String abstractText) {
        this.paperId = paperId;
        this.title = title;
        this.abstractText = abstractText;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
}