package com.alex.researchhub.service.researchtimeline;
import java.util.List;
import com.alex.researchhub.dto.researchtimeline.*;
public interface ResearchTimelineService {
    ResearchTimelineResponse createResearchTimeline(ResearchTimelineRequest data);
    ResearchTimelineResponse getResearchTimeline(Long id);
    List<ResearchTimelineResponse> getAllResearchTimelines();
    ResearchTimelineResponse updateResearchTimeline(Long id, ResearchTimelineRequest data);
    void deleteResearchTimeline(Long id);
}
