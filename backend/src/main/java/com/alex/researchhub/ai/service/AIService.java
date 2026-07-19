package com.alex.researchhub.ai.service;

import com.alex.researchhub.ai.client.AIClient;
import com.alex.researchhub.ai.dto.AIResponse;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final AIClient aiClient;

    public AIService(AIClient aiClient) {
        this.aiClient = aiClient;
    }

    public AIResponse summarize(String abstractText) {
        return aiClient.summarize(abstractText);
    }
}