package com.alex.researchhub.paper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "semantic.api")
public class SemanticScholarProperties {

    private String baseUrl;
    private String key;

}