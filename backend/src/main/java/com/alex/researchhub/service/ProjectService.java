package com.alex.researchhub.service;

import java.util.List;

import com.alex.researchhub.dto.ProjectRequest;
import com.alex.researchhub.dto.ProjectResponse;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest data);

    ProjectResponse getProject(Long id);

    List<ProjectResponse> getAllProjects();
    
    ProjectResponse updateProject(Long id, ProjectRequest data);

    void deleteProject(Long id);

    
} 
