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
import com.alex.researchhub.dto.collaboration.CollaborationRequest;
import com.alex.researchhub.dto.collaboration.CollaborationResponse;
import com.alex.researchhub.service.collaboration.CollaborationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/collaboration")
@RequiredArgsConstructor
public class CollaborationController {
    private final CollaborationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<CollaborationResponse>> create(@Valid @RequestBody CollaborationRequest req) {
        return ResponseBuilder.success("Collaboration Created Successfully", service.createCollaboration(req),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CollaborationResponse>> get(@PathVariable Long id) {
        return ResponseBuilder.success("Collaboration Retrieved successfully", service.getCollaboration(id),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CollaborationResponse>>> getAll() {
        return ResponseBuilder.success("retrieved all collaborations", service.getAllCollaborations(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CollaborationResponse>> update(@PathVariable Long id,
            @RequestBody CollaborationRequest req) {
        return ResponseBuilder.success("Updated existing collaboration successfully",
                service.updateCollaboration(id, req), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteCollaboration(id);
        return ResponseBuilder.success("Collaboration Deleted Successfully", HttpStatus.OK);
    }
}
