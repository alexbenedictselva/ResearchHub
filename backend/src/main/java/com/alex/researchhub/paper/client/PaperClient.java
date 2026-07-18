package com.alex.researchhub.paper.client;

// import com.yourpackage.paper.dto.PaperResponse;

import java.util.List;

import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.dto.TrendingResponse;

// import com.alex.researchhub.paper.config.PaperResponse;

public interface PaperClient {

    List<PaperResponse> searchPapers(String query);

    PaperDetailsResponse getPaperDetails(String paperId);

    List<AuthorResponse> searchAuthors(String name);

    List<PaperResponse> getLatestAIPapers();

    List<PaperResponse> getLatestCyberSecurityPapers();

    List<PaperResponse> getLatestCloudPapers();

    TrendingResponse getTrendingResearch();

}