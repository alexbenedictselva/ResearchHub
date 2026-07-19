package com.alex.researchhub.ai.dto;

import java.util.List;

public class NoveltyResponse {

    private double noveltyScore;

    private double highestSimilarity;

    private double averageSimilarity;

    private List<PaperSimilarity> topMatches;

    public NoveltyResponse() {
    }

    public NoveltyResponse(
            double noveltyScore,
            double highestSimilarity,
            double averageSimilarity,
            List<PaperSimilarity> topMatches) {

        this.noveltyScore = noveltyScore;
        this.highestSimilarity = highestSimilarity;
        this.averageSimilarity = averageSimilarity;
        this.topMatches = topMatches;
    }

    public double getNoveltyScore() {
        return noveltyScore;
    }

    public void setNoveltyScore(double noveltyScore) {
        this.noveltyScore = noveltyScore;
    }

    public double getHighestSimilarity() {
        return highestSimilarity;
    }

    public void setHighestSimilarity(double highestSimilarity) {
        this.highestSimilarity = highestSimilarity;
    }

    public double getAverageSimilarity() {
        return averageSimilarity;
    }

    public void setAverageSimilarity(double averageSimilarity) {
        this.averageSimilarity = averageSimilarity;
    }

    public List<PaperSimilarity> getTopMatches() {
        return topMatches;
    }

    public void setTopMatches(List<PaperSimilarity> topMatches) {
        this.topMatches = topMatches;
    }
}