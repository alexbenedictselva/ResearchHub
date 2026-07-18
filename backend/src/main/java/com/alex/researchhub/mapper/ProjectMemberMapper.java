package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.projectmember.*;
import com.alex.researchhub.entity.ProjectMember;

public class ProjectMemberMapper {
    public static ProjectMember toEntity(ProjectMemberRequest request) {
        return ProjectMember.builder().id(request.getId()).role(request.getRole()).build();
    }
    public static ProjectMemberResponse toResponse(ProjectMember member) {
        return ProjectMemberResponse.builder().id(member.getId()).role(member.getRole())
                .project(member.getProject() == null ? null : member.getProject().getId())
                .researcher(member.getResearcher() == null ? null : member.getResearcher().getId()).build();
    }
}
