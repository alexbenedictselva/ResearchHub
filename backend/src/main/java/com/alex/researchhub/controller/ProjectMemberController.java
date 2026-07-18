package com.alex.researchhub.controller;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.alex.researchhub.common.response.*;
import com.alex.researchhub.dto.projectmember.*;
import com.alex.researchhub.service.projectmember.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController @RequestMapping("/api/project-member") @RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService service;
    @PostMapping public ResponseEntity<ApiResponse<ProjectMemberResponse>> create(@Valid @RequestBody ProjectMemberRequest request) { return ResponseBuilder.success("Project member created successfully", service.createProjectMember(request), HttpStatus.CREATED); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<ProjectMemberResponse>> get(@PathVariable Long id) { return ResponseBuilder.success("Project member retrieved successfully", service.getProjectMember(id), HttpStatus.OK); }
    @GetMapping public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getAll() { return ResponseBuilder.success("Retrieved all project members", service.getAllProjectMembers(), HttpStatus.OK); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<ProjectMemberResponse>> update(@PathVariable Long id, @Valid @RequestBody ProjectMemberRequest request) { return ResponseBuilder.success("Project member updated successfully", service.updateProjectMember(id, request), HttpStatus.OK); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.deleteProjectMember(id); return ResponseBuilder.success("Project member deleted successfully", HttpStatus.OK); }
}
