package com.alex.researchhub.service.project;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.alex.researchhub.dto.project.ProjectRequest;
import com.alex.researchhub.dto.project.ProjectResponse;
import com.alex.researchhub.entity.Project;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.mapper.ProjectMapper;
import com.alex.researchhub.repository.ProjectRepository;
import com.alex.researchhub.repository.ResearcherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ResearcherRepository researcherRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest data) {
        Researcher researcher = researcherRepository.findById(data.getResearcher())
                .orElseThrow(() -> new RuntimeException("Researcher not found"));
        Project response = ProjectMapper.toEntity(data);
        response.setResearcher(researcher);
        projectRepository.save(response);
        return ProjectMapper.toResponse(response);
    }

    @Override
    public ProjectResponse getProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        return ProjectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> res = new ArrayList<>();
        for (Project i : projects) {
            res.add(ProjectMapper.toResponse(i));
        }
        return res;
    }


    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest data) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        project.setDescription(data.getDescription());
        project.setDomain(data.getDomain());
        project.setInnovationScore(data.getInnovationScore());
        Researcher researcher = researcherRepository.findById(data.getResearcher())
                .orElseThrow(() -> new RuntimeException("Researcher not found"));
        project.setResearcher(researcher);
        project.setStatus(data.getStatus());
        project.setTitle(data.getTitle());

        return ProjectMapper.toResponse(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
