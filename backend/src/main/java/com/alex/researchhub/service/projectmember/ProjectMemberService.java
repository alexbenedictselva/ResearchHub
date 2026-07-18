package com.alex.researchhub.service.projectmember;
import java.util.List;
import com.alex.researchhub.dto.projectmember.*;
public interface ProjectMemberService {
    ProjectMemberResponse createProjectMember(ProjectMemberRequest data);
    ProjectMemberResponse getProjectMember(Long id);
    List<ProjectMemberResponse> getAllProjectMembers();
    ProjectMemberResponse updateProjectMember(Long id, ProjectMemberRequest data);
    void deleteProjectMember(Long id);
}
