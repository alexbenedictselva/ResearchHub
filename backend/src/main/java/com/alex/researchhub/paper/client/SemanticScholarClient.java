package com.alex.researchhub.paper.client;

// import com.yourpackage.paper.dto.AuthorDto;
// import com.yourpackage.paper.dto.PaperResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.alex.researchhub.paper.dto.AuthorDto;
import com.alex.researchhub.paper.dto.AuthorResponse;
import com.alex.researchhub.paper.dto.CitationDto;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.dto.PaperFilterRequest;
import com.alex.researchhub.paper.dto.PaperResponse;
import com.alex.researchhub.paper.dto.ReferenceDto;
import com.alex.researchhub.paper.dto.TrendingResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
// @RequiredArgsConstructor
public class SemanticScholarClient implements PaperClient {

        // private final RestClient restClient;
        private final RestClient graphRestClient;
        private final RestClient recommendationRestClient;

        public SemanticScholarClient(
                        @Qualifier("graphRestClient") RestClient graphRestClient,
                        @Qualifier("recommendationRestClient") RestClient recommendationRestClient) {

                this.graphRestClient = graphRestClient;
                this.recommendationRestClient = recommendationRestClient;

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> searchPapers(String query) {

                Map<String, Object> response = graphRestClient.get()
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
                                                                                        String.valueOf(author.get(
                                                                                                        "authorId")))
                                                                        .name(
                                                                                        String.valueOf(author
                                                                                                        .get("name")))
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
                                                                        ? ((Number) paper.get("citationCount"))
                                                                                        .intValue()
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

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public PaperDetailsResponse getPaperDetails(String paperId) {

                Map<String, Object> response = graphRestClient.get()
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

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<AuthorResponse> searchAuthors(String name) {

                Map<String, Object> response = graphRestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/author/search")
                                                .queryParam("query", name)
                                                .queryParam("limit", 10)
                                                .queryParam(
                                                                "fields",
                                                                "authorId,name,affiliations,paperCount,citationCount,hIndex,homepage")
                                                .build())
                                .retrieve()
                                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                                });

                List<AuthorResponse> authors = new ArrayList<>();

                if (response == null) {
                        return authors;
                }

                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

                if (data == null) {
                        return authors;
                }

                for (Map<String, Object> author : data) {

                        String affiliation = null;

                        Object affiliations = author.get("affiliations");

                        if (affiliations instanceof List<?> list && !list.isEmpty()) {
                                affiliation = String.valueOf(list.get(0));
                        }

                        authors.add(
                                        AuthorResponse.builder()
                                                        .authorId(String.valueOf(author.get("authorId")))
                                                        .name(String.valueOf(author.get("name")))
                                                        .affiliation(affiliation)
                                                        .paperCount((Integer) author.get("paperCount"))
                                                        .citationCount((Integer) author.get("citationCount"))
                                                        .hIndex((Integer) author.get("hIndex"))
                                                        .homepage(
                                                                        author.get("homepage") == null
                                                                                        ? null
                                                                                        : String.valueOf(author.get(
                                                                                                        "homepage")))
                                                        .build());

                }

                return authors;

        }

        private List<PaperResponse> getRecentPapers(String topic) {

                Map<String, Object> response = graphRestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/paper/search")
                                                .queryParam("query", topic)
                                                .queryParam("limit", 10)
                                                .queryParam(
                                                                "fields",
                                                                "paperId,title,abstract,year,citationCount,url,authors,externalIds")
                                                .build())
                                .retrieve()
                                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                                });

                if (response == null) {
                        return Collections.emptyList();
                }

                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

                List<PaperResponse> papers = new ArrayList<>();

                if (data == null) {
                        return papers;
                }

                for (Map<String, Object> paper : data) {

                        List<AuthorDto> authors = new ArrayList<>();

                        List<Map<String, Object>> authorData = (List<Map<String, Object>>) paper.get("authors");

                        if (authorData != null) {

                                for (Map<String, Object> author : authorData) {

                                        authors.add(
                                                        AuthorDto.builder()
                                                                        .authorId(String.valueOf(
                                                                                        author.get("authorId")))
                                                                        .name(String.valueOf(author.get("name")))
                                                                        .build());

                                }

                        }

                        String doi = null;

                        if (paper.get("externalIds") != null) {

                                Map<String, Object> ids = (Map<String, Object>) paper.get("externalIds");

                                doi = ids.get("DOI") == null
                                                ? null
                                                : String.valueOf(ids.get("DOI"));

                        }

                        papers.add(
                                        PaperResponse.builder()
                                                        .paperId(String.valueOf(paper.get("paperId")))
                                                        .title(String.valueOf(paper.get("title")))
                                                        .abstractText(String.valueOf(paper.get("abstract")))
                                                        .year((Integer) paper.get("year"))
                                                        .citationCount((Integer) paper.get("citationCount"))
                                                        .doi(doi)
                                                        .url(String.valueOf(paper.get("url")))
                                                        .authors(authors)
                                                        .build());

                }

                return papers;

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> getLatestAIPapers() {

                return getRecentPapers("Artificial Intelligence");

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> getLatestCyberSecurityPapers() {

                return getRecentPapers("Cyber Security");

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> getLatestCloudPapers() {

                return getRecentPapers("Cloud Computing");

        }

        private List<PaperResponse> getMostCitedPapers() {

                Map<String, Object> response = graphRestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/paper/search")
                                                .queryParam("query",
                                                                "Artificial Intelligence")
                                                .queryParam("limit", 10)
                                                .queryParam(
                                                                "fields",
                                                                "paperId,title,abstract,year,citationCount,url,authors,externalIds")
                                                .build())
                                .retrieve()
                                .body(new ParameterizedTypeReference<>() {
                                });

                if (response == null) {

                        return Collections.emptyList();

                }

                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

                List<PaperResponse> papers = new ArrayList<>();

                if (data == null) {

                        return papers;

                }

                for (Map<String, Object> paper : data) {

                        List<AuthorDto> authors = new ArrayList<>();

                        List<Map<String, Object>> authorData = (List<Map<String, Object>>) paper.get("authors");

                        if (authorData != null) {

                                for (Map<String, Object> author : authorData) {

                                        authors.add(
                                                        AuthorDto.builder()
                                                                        .authorId(
                                                                                        String.valueOf(author.get(
                                                                                                        "authorId")))
                                                                        .name(
                                                                                        String.valueOf(author
                                                                                                        .get("name")))
                                                                        .build());

                                }

                        }

                        String doi = null;

                        if (paper.get("externalIds") != null) {

                                Map<String, Object> ids = (Map<String, Object>) paper.get("externalIds");

                                doi = ids.get("DOI") == null
                                                ? null
                                                : String.valueOf(ids.get("DOI"));

                        }

                        papers.add(
                                        PaperResponse.builder()
                                                        .paperId(
                                                                        String.valueOf(paper.get("paperId")))
                                                        .title(
                                                                        String.valueOf(paper.get("title")))
                                                        .abstractText(
                                                                        String.valueOf(paper.get("abstract")))
                                                        .year(
                                                                        (Integer) paper.get("year"))
                                                        .citationCount(
                                                                        (Integer) paper.get("citationCount"))
                                                        .doi(doi)
                                                        .url(
                                                                        String.valueOf(paper.get("url")))
                                                        .authors(authors)
                                                        .build());

                }

                papers.sort(
                                Comparator.comparing(
                                                PaperResponse::getCitationCount)
                                                .reversed());

                return papers;

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public TrendingResponse getTrendingResearch() {

                List<PaperResponse> papers = getMostCitedPapers();

                List<AuthorResponse> authors = searchAuthors("Artificial Intelligence");

                List<String> topics = List.of(
                                "Artificial Intelligence",
                                "Machine Learning",
                                "Large Language Models",
                                "Computer Vision",
                                "Cyber Security",
                                "Cloud Computing");

                return TrendingResponse.builder()
                                .mostCitedPapers(papers)
                                .popularAuthors(authors)
                                .trendingTopics(topics)
                                .build();

        }

        // @Override
        // public List<PaperResponse> getRelatedPapers(String paperId) {

        // Map<String, Object> response = graphRestClient.get()
        // .uri(uriBuilder -> uriBuilder
        // .path("/paper/{paperId}/related")
        // .queryParam("limit", 10)
        // .queryParam(
        // "fields",
        // "paperId,title,abstract,year,citationCount,url,authors,externalIds")
        // .build(paperId))
        // .retrieve()
        // .body(new ParameterizedTypeReference<Map<String, Object>>() {
        // });

        // if (response == null) {
        // return Collections.emptyList();
        // }

        // List<Map<String, Object>> data = (List<Map<String, Object>>)
        // response.get("data");

        // if (data == null) {
        // return Collections.emptyList();
        // }

        // List<PaperResponse> papers = new ArrayList<>();

        // for (Map<String, Object> paper : data) {

        // List<AuthorDto> authors = new ArrayList<>();

        // List<Map<String, Object>> authorData = (List<Map<String, Object>>)
        // paper.get("authors");

        // if (authorData != null) {

        // for (Map<String, Object> author : authorData) {

        // authors.add(
        // AuthorDto.builder()
        // .authorId(String.valueOf(
        // author.get("authorId")))
        // .name(String.valueOf(author.get("name")))
        // .build());

        // }

        // }

        // String doi = null;

        // if (paper.get("externalIds") != null) {

        // Map<String, Object> ids = (Map<String, Object>) paper.get("externalIds");

        // doi = ids.get("DOI") == null
        // ? null
        // : String.valueOf(ids.get("DOI"));

        // }

        // papers.add(
        // PaperResponse.builder()
        // .paperId(String.valueOf(paper.get("paperId")))
        // .title(String.valueOf(paper.get("title")))
        // .abstractText(String.valueOf(paper.get("abstract")))
        // .year((Integer) paper.get("year"))
        // .citationCount((Integer) paper.get("citationCount"))
        // .doi(doi)
        // .url(String.valueOf(paper.get("url")))
        // .authors(authors)
        // .build());

        // }

        // return papers;

        // }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> getSimilarPapers(String paperId) {

                Map<String, Object> response = recommendationRestClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/recommendations/v1/papers/forpaper/{paperId}")
                                                .queryParam("limit", 10)
                                                .queryParam(
                                                                "fields",
                                                                "paperId,title,abstract,year,citationCount,url,authors,externalIds")
                                                .build(paperId))
                                .retrieve()
                                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                                });

                if (response == null) {
                        return Collections.emptyList();
                }

                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("recommendedPapers");

                if (data == null) {
                        return Collections.emptyList();
                }

                List<PaperResponse> papers = new ArrayList<>();

                for (Map<String, Object> paper : data) {

                        List<AuthorDto> authors = new ArrayList<>();

                        List<Map<String, Object>> authorData = (List<Map<String, Object>>) paper.get("authors");

                        if (authorData != null) {

                                for (Map<String, Object> author : authorData) {

                                        authors.add(
                                                        AuthorDto.builder()
                                                                        .authorId(String.valueOf(
                                                                                        author.get("authorId")))
                                                                        .name(String.valueOf(author.get("name")))
                                                                        .build());

                                }

                        }

                        String doi = null;

                        if (paper.get("externalIds") != null) {

                                Map<String, Object> ids = (Map<String, Object>) paper.get("externalIds");

                                doi = ids.get("DOI") == null
                                                ? null
                                                : String.valueOf(ids.get("DOI"));

                        }

                        papers.add(
                                        PaperResponse.builder()
                                                        .paperId(String.valueOf(paper.get("paperId")))
                                                        .title(String.valueOf(paper.get("title")))
                                                        .abstractText((String) paper.get("abstract"))
                                                        .year((Integer) paper.get("year"))
                                                        .citationCount((Integer) paper.get("citationCount"))
                                                        .doi(doi)
                                                        .url((String) paper.get("url"))
                                                        .authors(authors)
                                                        .build());

                }

                return papers;

        }

        @Retryable(retryFor = HttpClientErrorException.TooManyRequests.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public List<PaperResponse> filterPapers(PaperFilterRequest request) {

                Map<String, Object> response = graphRestClient.get()
                                .uri(uriBuilder -> {

                                        uriBuilder.path("/paper/search");

                                        uriBuilder.queryParam(
                                                        "query",
                                                        request.getQuery());

                                        uriBuilder.queryParam(
                                                        "limit",
                                                        10);

                                        uriBuilder.queryParam(
                                                        "fields",
                                                        "paperId,title,abstract,year,citationCount,url,authors,externalIds,venue,openAccessPdf");

                                        return uriBuilder.build();

                                })
                                .retrieve()
                                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                                });

                if (response == null) {

                        return Collections.emptyList();

                }

                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

                if (data == null) {

                        return Collections.emptyList();

                }

                List<PaperResponse> papers = new ArrayList<>();

                for (Map<String, Object> paper : data) {

                        if (matchesFilter(paper, request)) {

                                papers.add(mapPaperResponse(paper));

                        }

                }

                return papers;

        }

        private boolean matchesFilter(
                        Map<String, Object> paper,
                        PaperFilterRequest request) {

                if (request.getYear() != null) {

                        Integer year = paper.get("year") == null
                                        ? null
                                        : ((Number) paper.get("year")).intValue();

                        if (!request.getYear().equals(year)) {

                                return false;

                        }

                }

                if (request.getJournal() != null) {

                        String venue = paper.get("venue") == null
                                        ? ""
                                        : paper.get("venue").toString();

                        if (!venue.equalsIgnoreCase(request.getJournal())) {

                                return false;

                        }

                }

                if (request.getMinCitationCount() != null) {

                        Integer citations = paper.get("citationCount") == null
                                        ? 0
                                        : ((Number) paper.get("citationCount")).intValue();

                        if (citations < request.getMinCitationCount()) {

                                return false;

                        }

                }

                if (request.getAuthor() != null) {

                        List<Map<String, Object>> authors = (List<Map<String, Object>>) paper.get("authors");

                        boolean found = false;

                        if (authors != null) {

                                for (Map<String, Object> author : authors) {

                                        if (request.getAuthor()
                                                        .equalsIgnoreCase(
                                                                        String.valueOf(author.get("name")))) {

                                                found = true;
                                                break;

                                        }

                                }

                        }

                        if (!found) {

                                return false;

                        }

                }

                if (request.getOpenAccess() != null) {

                        boolean openAccess = paper.get("openAccessPdf") != null;

                        if (request.getOpenAccess() != openAccess) {

                                return false;

                        }

                }

                return true;

        }

        private PaperResponse mapPaperResponse(Map<String, Object> paper) {

                List<AuthorDto> authors = new ArrayList<>();

                List<Map<String, Object>> authorData = (List<Map<String, Object>>) paper.get("authors");

                if (authorData != null) {

                        for (Map<String, Object> author : authorData) {

                                authors.add(
                                                AuthorDto.builder()
                                                                .authorId(String.valueOf(author.get("authorId")))
                                                                .name(String.valueOf(author.get("name")))
                                                                .build());

                        }

                }

                String doi = null;

                if (paper.get("externalIds") != null) {

                        Map<String, Object> ids = (Map<String, Object>) paper.get("externalIds");

                        doi = ids.get("DOI") == null
                                        ? null
                                        : String.valueOf(ids.get("DOI"));

                }

                return PaperResponse.builder()
                                .paperId(
                                                paper.get("paperId") != null
                                                                ? paper.get("paperId").toString()
                                                                : null)
                                .title(
                                                paper.get("title") != null
                                                                ? paper.get("title").toString()
                                                                : null)
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

        }
}
