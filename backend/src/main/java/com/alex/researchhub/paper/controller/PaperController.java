package com.alex.researchhub.paper.controller;

import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.DoiRequest;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperFilterRequest;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.dto.TrendingResponse;
import com.alex.researchhub.paper.service.PaperService;
import com.alex.researchhub.service.publication.PublicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
public class PaperController {

    private final PaperService paperService;

    // Accept userAbstract in POST body, extract keywords from AI service, then
    // search
    @PostMapping("/search")
    public List<PaperResponse> searchPapersByAbstract(
            @RequestBody com.alex.researchhub.paper.dto.UserAbstractRequest request) {

        org.springframework.web.client.RestTemplate rt = new org.springframework.web.client.RestTemplate();

        java.util.Map<String, Object> aiResponse = null;

        try {
            aiResponse = rt.postForObject(
                    "http://localhost:8000/ai/extract-keywords",
                    java.util.Collections.singletonMap("userAbstract", request.getUserAbstract()),
                    java.util.Map.class);
        } catch (Exception ex) {
            // If AI service unavailable, fall back to using the provided abstract as query
        }

        String query = request.getUserAbstract() == null ? "" : request.getUserAbstract();

        if (aiResponse != null && aiResponse.containsKey("keywords")) {
            Object kws = aiResponse.get("keywords");

            if (kws instanceof java.util.List) {
                @SuppressWarnings("unchecked")
                java.util.List<Object> kwList = (java.util.List<Object>) kws;

                java.util.List<String> topKeywords = kwList.stream()
                        .map(Object::toString)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .limit(5)
                        .collect(java.util.stream.Collectors.toList());

                if (!topKeywords.isEmpty()) {
                    query = String.join(" ", topKeywords);
                }

            }
        }

        return paperService.searchPapers(query);

    }

    @GetMapping("/{paperId}/details")
    public PaperDetailsResponse getPaperDetails(
            @PathVariable String paperId) {

        return paperService.getPaperDetails(paperId);

    }

    @GetMapping("/authors/search")
    public List<AuthorResponse> searchAuthors(
            @RequestParam String name) {

        return paperService.searchAuthors(name);

    }

    @GetMapping("/feed/ai")
    public List<PaperResponse> latestAI() {

        return paperService.getLatestAIPapers();

    }

    @GetMapping("/feed/cybersecurity")
    public List<PaperResponse> latestCyberSecurity() {

        return paperService.getLatestCyberSecurityPapers();

    }

    @GetMapping("/feed/cloud")
    public List<PaperResponse> latestCloud() {

        return paperService.getLatestCloudPapers();

    }

    @GetMapping("/trending")
    public TrendingResponse getTrendingResearch() {

        return paperService.getTrendingResearch();

    }

    @GetMapping("/{paperId}/similar")
    public List<PaperResponse> getSimilarPapers(
            @PathVariable String paperId) {

        return paperService.getSimilarPapers(paperId);

    }

    @PostMapping("/filter")
    public List<PaperResponse> filterPapers(
            @RequestBody PaperFilterRequest request) {

        return paperService.filterPapers(request);

    }
}