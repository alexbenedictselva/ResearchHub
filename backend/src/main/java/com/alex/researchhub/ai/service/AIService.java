package com.alex.researchhub.ai.service;

import com.alex.researchhub.ai.client.AIClient;
import com.alex.researchhub.ai.dto.AIResponse;
import com.alex.researchhub.ai.dto.NoveltyCheckRequest;
import com.alex.researchhub.ai.dto.NoveltyRequest;
import com.alex.researchhub.ai.dto.NoveltyResponse;
import com.alex.researchhub.ai.dto.PaperDTO;
import com.alex.researchhub.paper.dto.PaperDetailsResponse;
import com.alex.researchhub.paper.service.PaperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final AIClient aiClient;
    private final PaperService paperService;

    public AIService(AIClient aiClient, PaperService paperService) {
        this.aiClient = aiClient;
        this.paperService = paperService;
    }

    public AIResponse summarize(String abstractText) {
        return aiClient.summarize(abstractText);
    }

    public NoveltyResponse checkNovelty(NoveltyCheckRequest request) {
        List<PaperDTO> papers = request.getPapers().stream().map(item -> {
            PaperDetailsResponse details = paperService.getPaperDetails(item.getPaperId());
            return new PaperDTO(item.getPaperId(), details.getTitle(), details.getAbstractText());
        }).toList();

        return aiClient.checkNovelty(new NoveltyRequest(request.getUserAbstract(), papers));
    }
}