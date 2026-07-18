package com.alex.researchhub.paper.controller;

import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{paperId}")
    public PaperDetailsResponse getPaperDetails(
            @PathVariable String paperId) {

        return paperService.getPaperDetails(paperId);

    }
}