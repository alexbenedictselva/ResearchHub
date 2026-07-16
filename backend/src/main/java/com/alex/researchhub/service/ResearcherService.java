package com.alex.researchhub.service;

import com.alex.researchhub.dto.ResearcherRequest;
import com.alex.researchhub.dto.*;

import java.util.List;

public interface ResearcherService {

    ResearcherResponse createResearcher(ResearcherRequest request);

    ResearcherResponse getResearcher(Long id);

    List<ResearcherResponse> getAllResearchers();

    ResearcherResponse updateResearcher(Long id,
            ResearcherRequest request);

    void deleteResearcher(Long id);

}