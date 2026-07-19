package com.alex.researchhub.ai.dto;

public class AIResponse {

    private String summary;

    public AIResponse() {
    }

    public AIResponse(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}