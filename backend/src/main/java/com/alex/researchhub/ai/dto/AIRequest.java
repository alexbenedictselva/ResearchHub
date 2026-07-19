package com.alex.researchhub.ai.dto;

public class AIRequest {

    private String abstractText;

    public AIRequest() {
    }

    public AIRequest(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
}