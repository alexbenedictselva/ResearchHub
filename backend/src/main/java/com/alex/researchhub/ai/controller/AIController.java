package com.alex.researchhub.ai.controller;

import com.alex.researchhub.ai.dto.AIRequest;
import com.alex.researchhub.ai.dto.AIResponse;
import com.alex.researchhub.ai.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/summarize")
    public AIResponse summarize(@RequestBody AIRequest request) {

        return aiService.summarize(
                request.getAbstractText()
        );
    }
}