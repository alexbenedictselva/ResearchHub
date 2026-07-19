package com.alex.researchhub.ai.client;

import com.alex.researchhub.ai.dto.AIResponse;
import com.alex.researchhub.ai.dto.NoveltyRequest;
import com.alex.researchhub.ai.dto.NoveltyResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class AIClient {

    private final RestClient restClient;

    public AIClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8000")
                .build();
    }

    // ===========================
    // Summarization
    // ===========================

    public AIResponse summarize(String abstractText) {

        Map<String, String> request = Map.of(
                "abstract", abstractText
        );

        return restClient.post()
                .uri("/ai/summarize")
                .body(request)
                .retrieve()
                .body(AIResponse.class);
    }

    // ===========================
    // Novelty Checker
    // ===========================

    public NoveltyResponse checkNovelty(NoveltyRequest request) {

        return restClient.post()
                .uri("/ai/novelty-check")
                .body(request)
                .retrieve()
                .body(NoveltyResponse.class);

    }

}