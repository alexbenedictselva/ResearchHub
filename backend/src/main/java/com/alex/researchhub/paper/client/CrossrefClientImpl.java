package com.alex.researchhub.paper.client;

// import com.alex.researchhub.publication.dto.CrossrefResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.alex.researchhub.paper.dto.CrossrefResponse;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CrossrefClientImpl implements CrossrefClient {

        // private final RestClient restClient;
        private final RestClient graphRestClient;
        private final RestClient recommendationRestClient;

        @Override
        public CrossrefResponse fetchByDoi(String doi) {

                Map<String, Object> response = graphRestClient.get()
                                .uri("https://api.crossref.org/works/{doi}", doi)
                                .retrieve()
                                .body(new ParameterizedTypeReference<>() {
                                });

                Map<String, Object> message = (Map<String, Object>) response.get("message");

                List<String> authors = new ArrayList<>();

                List<Map<String, Object>> authorList = (List<Map<String, Object>>) message.get("author");

                if (authorList != null) {

                        for (Map<String, Object> author : authorList) {

                                String given = String.valueOf(author.get("given"));
                                String family = String.valueOf(author.get("family"));

                                authors.add(given + " " + family);

                        }

                }

                Integer year = null;

                Map<String, Object> published = (Map<String, Object>) message.get("published-print");

                if (published == null) {
                        published = (Map<String, Object>) message.get("published-online");
                }

                if (published != null) {

                        List<List<Integer>> parts = (List<List<Integer>>) published.get("date-parts");

                        if (parts != null && !parts.isEmpty()) {
                                year = parts.get(0).get(0);
                        }

                }

                return CrossrefResponse.builder()
                                .title(((List<String>) message.get("title")).get(0))
                                .authors(authors)
                                .journal(
                                                ((List<String>) message.get("container-title")).isEmpty()
                                                                ? null
                                                                : ((List<String>) message.get("container-title"))
                                                                                .get(0))
                                .year(year)
                                .doi(String.valueOf(message.get("DOI")))
                                .build();

        }

}