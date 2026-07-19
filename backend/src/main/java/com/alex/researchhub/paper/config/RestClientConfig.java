package com.alex.researchhub.paper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean("graphRestClient")
    public RestClient graphRestClient(SemanticScholarProperties properties) {

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("x-api-key", properties.getKey())
                .build();
    }

    @Bean("recommendationRestClient")
    public RestClient recommendationRestClient(SemanticScholarProperties properties) {

        return RestClient.builder()
                .baseUrl("https://api.semanticscholar.org")
                .defaultHeader("x-api-key", properties.getKey())
                .build();

    }
}