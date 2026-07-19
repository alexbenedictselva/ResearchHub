package com.alex.researchhub.paper.service;

import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.DoiRequest;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperFilterRequest;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.dto.TrendingResponse;

import java.util.List;

public interface PaperService {

    List<PaperResponse> searchPapers(String query);

    PaperDetailsResponse getPaperDetails(String paperId);

    List<AuthorResponse> searchAuthors(String name);

    void importByDoi(DoiRequest request);

    List<PaperResponse> getLatestAIPapers();

    List<PaperResponse> getLatestCyberSecurityPapers();

    List<PaperResponse> getLatestCloudPapers();

    TrendingResponse getTrendingResearch();

    List<PaperResponse> getSimilarPapers(String paperId);
    List<PaperResponse> filterPapers(PaperFilterRequest request);
}