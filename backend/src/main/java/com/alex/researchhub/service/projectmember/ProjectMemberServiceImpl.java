package com.alex.researchhub.service.projectmember;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alex.researchhub.dto.projectmember.*;
import com.alex.researchhub.entity.*;
import com.alex.researchhub.mapper.ProjectMemberMapper;
import com.alex.researchhub.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository; private final ProjectRepository projectRepository; private final ResearcherRepository researcherRepository;
    public ProjectMemberResponse createProjectMember(ProjectMemberRequest data) { ProjectMember member = ProjectMemberMapper.toEntity(data); setRelations(member, data); return ProjectMemberMapper.toResponse(projectMemberRepository.save(member)); }
    public ProjectMemberResponse getProjectMember(Long id) { return ProjectMemberMapper.toResponse(member(id)); }
    public List<ProjectMemberResponse> getAllProjectMembers() { return projectMemberRepository.findAll().stream().map(ProjectMemberMapper::toResponse).toList(); }
    public ProjectMemberResponse updateProjectMember(Long id, ProjectMemberRequest data) { ProjectMember member = member(id); setRelations(member, data); return ProjectMemberMapper.toResponse(projectMemberRepository.save(member)); }
    public void deleteProjectMember(Long id) { projectMemberRepository.delete(member(id)); }
    private void setRelations(ProjectMember member, ProjectMemberRequest data) { member.setProject(projectRepository.findById(data.getProject()).orElseThrow(() -> new EntityNotFoundException("Project not found"))); member.setResearcher(researcherRepository.findById(data.getResearcher()).orElseThrow(() -> new EntityNotFoundException("Researcher not found"))); member.setRole(data.getRole()); }
    private ProjectMember member(Long id) { return projectMemberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project member not found")); }
}
