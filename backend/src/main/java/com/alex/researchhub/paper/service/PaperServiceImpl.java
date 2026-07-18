package com.alex.researchhub.paper.service;

import com.alex.researchhub.paper.client.PaperClient;
import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {

    private final PaperClient paperClient;

    @Override
    public List<PaperResponse> searchPapers(String query) {

        return paperClient.searchPapers(query);

    }

    @Override
    public PaperDetailsResponse getPaperDetails(String paperId) {

        return paperClient.getPaperDetails(paperId);

    }

    @Override
    public List<AuthorResponse> searchAuthors(String name) {

        return paperClient.searchAuthors(name);

    }
}