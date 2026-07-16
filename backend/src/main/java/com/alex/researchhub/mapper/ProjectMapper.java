package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.ProjectRequest;
import com.alex.researchhub.dto.ProjectResponse;
import com.alex.researchhub.entity.Project;

public class ProjectMapper {
    public static Project toEntity(ProjectRequest req) {
        return Project.builder().id(req.getId()).description(req.getDescription()).domain(req.getDomain())
                .innovationScore(req.getInnovationScore()).status(req.getStatus()).title(req.getTitle()).build();
    }

    public static ProjectResponse toResponse(Project req) {
        return ProjectResponse.builder()
                .id(req.getId())
                .researcher(req.getResearcher() != null ? req.getResearcher().getId() : null)
                .title(req.getTitle())
                .description(req.getDescription())
                .domain(req.getDomain())
                .status(req.getStatus())
                .innovationScore(req.getInnovationScore())
                .build();
    }
}
