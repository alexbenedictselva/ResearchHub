package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.researchtimeline.*;
import com.alex.researchhub.entity.ResearchTimeline;

public class ResearchTimelineMapper {
    public static ResearchTimeline toEntity(ResearchTimelineRequest request) {
        return ResearchTimeline.builder().id(request.getId()).stage(request.getStage()).remarks(request.getRemarks()).date(request.getDate()).build();
    }
    public static ResearchTimelineResponse toResponse(ResearchTimeline timeline) {
        return ResearchTimelineResponse.builder().id(timeline.getId()).stage(timeline.getStage()).remarks(timeline.getRemarks())
                .date(timeline.getDate()).project(timeline.getProject() == null ? null : timeline.getProject().getId()).build();
    }
}
