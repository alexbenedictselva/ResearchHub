package com.alex.researchhub.paper.client;

// import com.yourpackage.paper.dto.AuthorDto;
// import com.yourpackage.paper.dto.PaperResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.alex.researchhub.paper.dto.AuthorDto;
import com.alex.researchhub.paper.dto.CitationDto;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.dto.ReferenceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SemanticScholarClient implements PaperClient {

    private final RestClient restClient;

    @Override
    public List<PaperResponse> searchPapers(String query) {

        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paper/search")
                        .queryParam("query", query)
                        .queryParam("limit", 10)
                        .queryParam(
                                "fields",
                                "paperId,title,abstract,year,citationCount,externalIds,url,authors")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        List<PaperResponse> papers = new ArrayList<>();

        if (response == null) {
            return papers;
        }

        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

        if (data == null) {
            return papers;
        }

        for (Map<String, Object> paper : data) {

            List<AuthorDto> authors = new ArrayList<>();

            List<Map<String, Object>> authorList = (List<Map<String, Object>>) paper.get("authors");

            if (authorList != null) {

                for (Map<String, Object> author : authorList) {

                    authors.add(
                            AuthorDto.builder()
                                    .authorId(
                                            String.valueOf(author.get("authorId")))
                                    .name(
                                            String.valueOf(author.get("name")))
                                    .build());
                }

            }

            String doi = null;

            Map<String, Object> externalIds = (Map<String, Object>) paper.get("externalIds");

            if (externalIds != null) {

                Object doiValue = externalIds.get("DOI");

                if (doiValue != null) {
                    doi = doiValue.toString();
                }

            }

            PaperResponse paperResponse = PaperResponse.builder()
                    .paperId(
                            String.valueOf(paper.get("paperId")))
                    .title(
                            String.valueOf(paper.get("title")))
                    .abstractText(
                            paper.get("abstract") != null
                                    ? paper.get("abstract").toString()
                                    : null)
                    .year(
                            paper.get("year") != null
                                    ? ((Number) paper.get("year")).intValue()
                                    : null)
                    .citationCount(
                            paper.get("citationCount") != null
                                    ? ((Number) paper.get("citationCount")).intValue()
                                    : 0)
                    .doi(doi)
                    .url(
                            paper.get("url") != null
                                    ? paper.get("url").toString()
                                    : null)
                    .authors(authors)
                    .build();

            papers.add(paperResponse);

        }

        return papers;

    }

    @Override
    public PaperDetailsResponse getPaperDetails(String paperId) {

        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paper/{paperId}")
                        .queryParam(
                                "fields",
                                "paperId,title,abstract,year,citationCount,referenceCount,venue,journal,publicationDate,url,externalIds,authors,references,citations")
                        .build(paperId))
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        if (response == null) {
            return null;
        }

        List<AuthorDto> authors = new ArrayList<>();

        List<Map<String, Object>> authorData = (List<Map<String, Object>>) response.get("authors");

        if (authorData != null) {

            for (Map<String, Object> author : authorData) {

                authors.add(
                        AuthorDto.builder()
                                .authorId(String.valueOf(author.get("authorId")))
                                .name(String.valueOf(author.get("name")))
                                .build());

            }
        }

        List<ReferenceDto> references = new ArrayList<>();

        List<Map<String, Object>> referenceData = (List<Map<String, Object>>) response.get("references");

        if (referenceData != null) {

            for (Map<String, Object> ref : referenceData) {

                references.add(
                        ReferenceDto.builder()
                                .paperId(String.valueOf(ref.get("paperId")))
                                .title(String.valueOf(ref.get("title")))
                                .build());

            }
        }

        List<CitationDto> citations = new ArrayList<>();

        List<Map<String, Object>> citationData = (List<Map<String, Object>>) response.get("citations");

        if (citationData != null) {

            for (Map<String, Object> citation : citationData) {

                citations.add(
                        CitationDto.builder()
                                .paperId(String.valueOf(citation.get("paperId")))
                                .title(String.valueOf(citation.get("title")))
                                .build());

            }
        }

        return PaperDetailsResponse.builder()
                .paperId(String.valueOf(response.get("paperId")))
                .title(String.valueOf(response.get("title")))
                .abstractText(String.valueOf(response.get("abstract")))
                .year((Integer) response.get("year"))
                .citationCount((Integer) response.get("citationCount"))
                .referenceCount((Integer) response.get("referenceCount"))
                .venue(String.valueOf(response.get("venue")))
                .url(String.valueOf(response.get("url")))
                .doi(response.containsKey("externalIds")
                        ? String.valueOf(((Map<?, ?>) response.get("externalIds")).get("DOI"))
                        : null)
                .authors(authors)
                .references(references)
                .citations(citations)
                .similarPapers(new ArrayList<>())
                .build();
    }

}