package com.alex.researchhub.controller;

import com.alex.researchhub.common.response.ApiResponse;
import com.alex.researchhub.common.response.ResponseBuilder;
import com.alex.researchhub.dto.researcher.ResearcherRequest;
import com.alex.researchhub.dto.researcher.ResearcherResponse;
import com.alex.researchhub.service.researcher.ResearcherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/researchers")
@RequiredArgsConstructor
public class ResearcherController {

    private final ResearcherService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ResearcherResponse>> create(
            @Valid @RequestBody ResearcherRequest request) {

        return ResponseBuilder.success(
                "Researcher created successfully",
                service.createResearcher(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResearcherResponse>> get(
            @PathVariable Long id) {

        return ResponseBuilder.success(
                "Researcher retrieved successfully",
                service.getResearcher(id),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResearcherResponse>>> getAll() {

        return ResponseBuilder.success(
                "Researchers retrieved successfully",
                service.getAllResearchers(),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")    
    public ResponseEntity<ApiResponse<ResearcherResponse>> update(
            @PathVariable Long id,
            @RequestBody ResearcherRequest request) {

        return ResponseBuilder.success(
                "Researcher updated successfully",
                service.updateResearcher(id, request),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.deleteResearcher(id);

        return ResponseBuilder.success(
                "Researcher deleted successfully",
                HttpStatus.OK);
    }

}