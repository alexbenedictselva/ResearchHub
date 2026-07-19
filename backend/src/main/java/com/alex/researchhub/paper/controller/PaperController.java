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

    @GetMapping("/search")
    public List<PaperResponse> searchPapers(
            @RequestParam String query) {

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