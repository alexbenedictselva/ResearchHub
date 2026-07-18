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
import com.alex.researchhub.dto.publication.PublicationRequest;
import com.alex.researchhub.dto.publication.PublicationResponse;
import com.alex.researchhub.service.publication.PublicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/publication")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PublicationResponse>> create(@Valid @RequestBody PublicationRequest req) {
        return ResponseBuilder.success("Publication Created Successfully", service.createPublication(req),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PublicationResponse>> get(@PathVariable Long id) {
        return ResponseBuilder.success("Publication Retrieved successfully", service.getPublication(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PublicationResponse>>> getAll() {
        return ResponseBuilder.success("retrieved all publications", service.getAllPublications(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PublicationResponse>> update(@PathVariable Long id,
            @RequestBody PublicationRequest req) {
        return ResponseBuilder.success("Updated existing publication successfully", service.updatePublication(id, req),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deletePublication(id);
        return ResponseBuilder.success("Publication Deleted Successfully", HttpStatus.OK);
    }
}
