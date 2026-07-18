package com.alex.researchhub.paper.service;

import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;

import java.util.List;

public interface PaperService {

    List<PaperResponse> searchPapers(String query);
    PaperDetailsResponse getPaperDetails(String paperId);
}