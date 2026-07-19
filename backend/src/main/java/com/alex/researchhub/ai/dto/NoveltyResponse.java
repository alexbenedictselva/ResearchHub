package com.alex.researchhub.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class NoveltyResponse {

    private double innovationScore;
    private SemanticSimilarity semanticSimilarity;
    private List<TopMatch> topMatches;
    private KeywordNovelty keywordNovelty;
    private ResearchMaturity researchMaturity;
    private Report report;

    public double getInnovationScore() { return innovationScore; }
    public void setInnovationScore(double innovationScore) { this.innovationScore = innovationScore; }

    public SemanticSimilarity getSemanticSimilarity() { return semanticSimilarity; }
    public void setSemanticSimilarity(SemanticSimilarity semanticSimilarity) { this.semanticSimilarity = semanticSimilarity; }

    public List<TopMatch> getTopMatches() { return topMatches; }
    public void setTopMatches(List<TopMatch> topMatches) { this.topMatches = topMatches; }

    public KeywordNovelty getKeywordNovelty() { return keywordNovelty; }
    public void setKeywordNovelty(KeywordNovelty keywordNovelty) { this.keywordNovelty = keywordNovelty; }

    public ResearchMaturity getResearchMaturity() { return researchMaturity; }
    public void setResearchMaturity(ResearchMaturity researchMaturity) { this.researchMaturity = researchMaturity; }

    public Report getReport() { return report; }
    public void setReport(Report report) { this.report = report; }

    public static class SemanticSimilarity {
        private double highest;
        private double average;
        private double lowest;

        public double getHighest() { return highest; }
        public void setHighest(double highest) { this.highest = highest; }

        public double getAverage() { return average; }
        public void setAverage(double average) { this.average = average; }

        public double getLowest() { return lowest; }
        public void setLowest(double lowest) { this.lowest = lowest; }
    }

    public static class TopMatch {
        private String paperId;
        private String title;
        private double similarity;
        private Integer year;
        private Integer citationCount;

        public String getPaperId() { return paperId; }
        public void setPaperId(String paperId) { this.paperId = paperId; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public double getSimilarity() { return similarity; }
        public void setSimilarity(double similarity) { this.similarity = similarity; }

        public Integer getYear() { return year; }
        public void setYear(Integer year) { this.year = year; }

        public Integer getCitationCount() { return citationCount; }
        public void setCitationCount(Integer citationCount) { this.citationCount = citationCount; }
    }

    public static class KeywordNovelty {
        private List<String> userKeywords;
        private List<String> novelKeywords;
        private List<String> overlapKeywords;
        private double keywordNoveltyScore;

        public List<String> getUserKeywords() { return userKeywords; }
        public void setUserKeywords(List<String> userKeywords) { this.userKeywords = userKeywords; }

        public List<String> getNovelKeywords() { return novelKeywords; }
        public void setNovelKeywords(List<String> novelKeywords) { this.novelKeywords = novelKeywords; }

        public List<String> getOverlapKeywords() { return overlapKeywords; }
        public void setOverlapKeywords(List<String> overlapKeywords) { this.overlapKeywords = overlapKeywords; }

        public double getKeywordNoveltyScore() { return keywordNoveltyScore; }
        public void setKeywordNoveltyScore(double keywordNoveltyScore) { this.keywordNoveltyScore = keywordNoveltyScore; }
    }

    public static class ResearchMaturity {
        private Integer averageYear;
        private Integer averageCitationCount;
        private String maturityLabel;
        private double maturityScore;

        public Integer getAverageYear() { return averageYear; }
        public void setAverageYear(Integer averageYear) { this.averageYear = averageYear; }

        public Integer getAverageCitationCount() { return averageCitationCount; }
        public void setAverageCitationCount(Integer averageCitationCount) { this.averageCitationCount = averageCitationCount; }

        public String getMaturityLabel() { return maturityLabel; }
        public void setMaturityLabel(String maturityLabel) { this.maturityLabel = maturityLabel; }

        public double getMaturityScore() { return maturityScore; }
        public void setMaturityScore(double maturityScore) { this.maturityScore = maturityScore; }
    }

    public static class Report {
        private List<String> strengths;
        private List<String> weaknesses;
        private List<String> recommendations;
        private String summary;

        public List<String> getStrengths() { return strengths; }
        public void setStrengths(List<String> strengths) { this.strengths = strengths; }

        public List<String> getWeaknesses() { return weaknesses; }
        public void setWeaknesses(List<String> weaknesses) { this.weaknesses = weaknesses; }

        public List<String> getRecommendations() { return recommendations; }
        public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
    }
}
