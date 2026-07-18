package com.alex.researchhub.paper.service;

import com.alex.researchhub.entity.Project;
import com.alex.researchhub.entity.Publication;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.paper.client.CrossrefClient;
import com.alex.researchhub.paper.client.PaperClient;
import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.CrossrefResponse;
import com.alex.researchhub.paper.dto.DoiRequest;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.repository.ProjectRepository;
import com.alex.researchhub.repository.PublicationRepository;
import com.alex.researchhub.repository.ResearcherRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {

    private final PaperClient paperClient;
    private final CrossrefClient crossrefClient;

    private final PublicationRepository publicationRepository;
    private final ProjectRepository projectRepository;
    private final ResearcherRepository researcherRepository;


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

    @Override
    public void importByDoi(DoiRequest request) {

        CrossrefResponse response = crossrefClient.fetchByDoi(request.getDoi());

        Researcher researcher = researcherRepository
                .findById(request.getResearcherId())
                .orElseThrow(() -> new RuntimeException("Researcher not found"));

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Publication publication = Publication.builder()
                .title(response.getTitle())
                .doi(response.getDoi())
                .journal(response.getJournal())
                .publicationYear(response.getYear())
                .researcher(researcher)
                .project(project)
                .build();

        publicationRepository.save(publication);

    }
}