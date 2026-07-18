package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.publication.PublicationRequest;
import com.alex.researchhub.dto.publication.PublicationResponse;
import com.alex.researchhub.entity.Publication;

public class PublicationMapper {
    public static Publication toEntity(PublicationRequest req) {
        return Publication.builder()
                .id(req.getId())
                .title(req.getTitle())
                .abstractText(req.getAbstractText())
                .doi(req.getDoi())
                .journal(req.getJournal())
                .conference(req.getConference())
                .publicationYear(req.getPublicationYear())
                .pdfUrl(req.getPdfUrl())
                .build();
    }

    public static PublicationResponse toResponse(Publication req) {
        return PublicationResponse.builder()
                .id(req.getId())
                .researcher(req.getResearcher() != null ? req.getResearcher().getId() : null)
                .project(req.getProject() != null ? req.getProject().getId() : null)
                .title(req.getTitle())
                .abstractText(req.getAbstractText())
                .doi(req.getDoi())
                .journal(req.getJournal())
                .conference(req.getConference())
                .publicationYear(req.getPublicationYear())
                .pdfUrl(req.getPdfUrl())
                .build();
    }
}
