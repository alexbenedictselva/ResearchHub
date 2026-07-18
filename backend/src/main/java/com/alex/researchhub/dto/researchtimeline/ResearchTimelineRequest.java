package com.alex.researchhub.dto.researchtimeline;

import java.time.LocalDate;
import com.alex.researchhub.entity.TimelineStage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResearchTimelineRequest {
    private Long id;
    @NotNull private Long project;
    @NotNull private TimelineStage stage;
    private String remarks;
    private LocalDate date;
}
