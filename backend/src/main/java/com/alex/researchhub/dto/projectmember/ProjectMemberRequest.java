package com.alex.researchhub.dto.projectmember;

import com.alex.researchhub.entity.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberRequest {
    private Long id;
    @NotNull private Long project;
    @NotNull private Long researcher;
    @NotNull private ProjectMemberRole role;
}
