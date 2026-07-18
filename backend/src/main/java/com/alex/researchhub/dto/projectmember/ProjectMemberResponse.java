package com.alex.researchhub.dto.projectmember;

import com.alex.researchhub.entity.ProjectMemberRole;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ProjectMemberResponse {
    private Long id;
    private Long project;
    private Long researcher;
    private ProjectMemberRole role;
}
