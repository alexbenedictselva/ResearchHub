package com.alex.researchhub.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.alex.researchhub.dto.publication.PublicationRequest;
import com.alex.researchhub.dto.publication.PublicationResponse;
import com.alex.researchhub.entity.Project;
import com.alex.researchhub.entity.Publication;
import com.alex.researchhub.entity.Researcher;

class PublicationMapperTest {

    @Test
    void shouldMapRequestToEntityAndResponse() {
        PublicationRequest request = new PublicationRequest();
        request.setId(7L);
        request.setResearcher(3L);
        request.setProject(9L);
        request.setTitle("Deep Learning in Research");
        request.setAbstractText("A study on multimodal learning");
        request.setDoi("10.1000/abc");
        request.setJournal("Nature");
        request.setConference("ICML");
        request.setPublicationYear(2024);
        request.setPdfUrl("https://example.com/paper.pdf");

        Publication entity = PublicationMapper.toEntity(request);

        assertEquals(7L, entity.getId());
        assertEquals("Deep Learning in Research", entity.getTitle());
        assertEquals("A study on multimodal learning", entity.getAbstractText());
        assertEquals("10.1000/abc", entity.getDoi());
        assertEquals("Nature", entity.getJournal());
        assertEquals("ICML", entity.getConference());
        assertEquals(2024, entity.getPublicationYear());
        assertEquals("https://example.com/paper.pdf", entity.getPdfUrl());

        Publication publication = Publication.builder()
                .id(7L)
                .researcher(Researcher.builder().id(3L).build())
                .project(Project.builder().id(9L).build())
                .title("Deep Learning in Research")
                .abstractText("A study on multimodal learning")
                .doi("10.1000/abc")
                .journal("Nature")
                .conference("ICML")
                .publicationYear(2024)
                .pdfUrl("https://example.com/paper.pdf")
                .createdAt(LocalDateTime.of(2024, 1, 1, 10, 30))
                .build();

        PublicationResponse response = PublicationMapper.toResponse(publication);

        assertEquals(7L, response.getId());
        assertEquals(3L, response.getResearcher());
        assertEquals(9L, response.getProject());
        assertEquals("Deep Learning in Research", response.getTitle());
        assertEquals("A study on multimodal learning", response.getAbstractText());
        assertEquals("10.1000/abc", response.getDoi());
        assertEquals("Nature", response.getJournal());
        assertEquals("ICML", response.getConference());
        assertEquals(2024, response.getPublicationYear());
        assertEquals("https://example.com/paper.pdf", response.getPdfUrl());
    }
}
