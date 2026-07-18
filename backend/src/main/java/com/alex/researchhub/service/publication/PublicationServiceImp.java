package com.alex.researchhub.service.publication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.researchhub.dto.publication.PublicationRequest;
import com.alex.researchhub.dto.publication.PublicationResponse;
import com.alex.researchhub.entity.Project;
import com.alex.researchhub.entity.Publication;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.mapper.PublicationMapper;
import com.alex.researchhub.repository.ProjectRepository;
import com.alex.researchhub.repository.PublicationRepository;
import com.alex.researchhub.repository.ResearcherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicationServiceImp implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final ResearcherRepository researcherRepository;
    private final ProjectRepository projectRepository;

    @Override
    public PublicationResponse createPublication(PublicationRequest data) {
        Researcher researcher = researcherRepository.findById(data.getResearcher())
                .orElseThrow(() -> new RuntimeException("Researcher not found"));
        Project project = projectRepository.findById(data.getProject())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Publication publication = PublicationMapper.toEntity(data);
        publication.setResearcher(researcher);
        publication.setProject(project);
        publicationRepository.save(publication);
        return PublicationMapper.toResponse(publication);
    }

    @Override
    public PublicationResponse getPublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        return PublicationMapper.toResponse(publication);
    }

    @Override
    public List<PublicationResponse> getAllPublications() {
        List<Publication> publications = publicationRepository.findAll();
        List<PublicationResponse> responses = new ArrayList<>();
        for (Publication publication : publications) {
            responses.add(PublicationMapper.toResponse(publication));
        }
        return responses;
    }

    @Override
    public PublicationResponse updatePublication(Long id, PublicationRequest data) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        publication.setTitle(data.getTitle());
        publication.setAbstractText(data.getAbstractText());
        publication.setDoi(data.getDoi());
        publication.setJournal(data.getJournal());
        publication.setConference(data.getConference());
        publication.setPublicationYear(data.getPublicationYear());
        publication.setPdfUrl(data.getPdfUrl());

        Researcher researcher = researcherRepository.findById(data.getResearcher())
                .orElseThrow(() -> new RuntimeException("Researcher not found"));
        Project project = projectRepository.findById(data.getProject())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        publication.setResearcher(researcher);
        publication.setProject(project);

        return PublicationMapper.toResponse(publication);
    }

    @Override
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }
}
