package com.alex.researchhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.researchhub.common.response.ApiResponse;
import com.alex.researchhub.common.response.ResponseBuilder;
import com.alex.researchhub.dto.project.ProjectRequest;
import com.alex.researchhub.dto.project.ProjectResponse;
import com.alex.researchhub.service.project.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> create(@Valid 
    @RequestBody ProjectRequest req) {
        return ResponseBuilder.success("Project Created Successfully", service.createProject(req), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> get(@PathVariable Long id) {
        return ResponseBuilder.success("Project Retrieved successfully", service.getProject(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAll() {
        return ResponseBuilder.success("retrieved all projects", service.getAllProjects(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> update(@PathVariable Long id, @RequestBody ProjectRequest req) {
        return ResponseBuilder.success("Updated existing project successfully", service.updateProject(id, req),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseBuilder.success("Project Deleted Successfully", HttpStatus.OK);
    }
}
