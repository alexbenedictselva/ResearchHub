package com.alex.researchhub.dto.researchtimeline;

import java.time.LocalDate;
import com.alex.researchhub.entity.TimelineStage;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ResearchTimelineResponse {
    private Long id;
    private Long project;
    private TimelineStage stage;
    private String remarks;
    private LocalDate date;
}
