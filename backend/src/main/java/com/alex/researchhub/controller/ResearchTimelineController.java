package com.alex.researchhub.controller;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.alex.researchhub.common.response.*;
import com.alex.researchhub.dto.researchtimeline.*;
import com.alex.researchhub.service.researchtimeline.ResearchTimelineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController @RequestMapping("/api/research-timeline") @RequiredArgsConstructor
public class ResearchTimelineController {
    private final ResearchTimelineService service;
    @PostMapping public ResponseEntity<ApiResponse<ResearchTimelineResponse>> create(@Valid @RequestBody ResearchTimelineRequest request) { return ResponseBuilder.success("Research timeline created successfully", service.createResearchTimeline(request), HttpStatus.CREATED); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<ResearchTimelineResponse>> get(@PathVariable Long id) { return ResponseBuilder.success("Research timeline retrieved successfully", service.getResearchTimeline(id), HttpStatus.OK); }
    @GetMapping public ResponseEntity<ApiResponse<List<ResearchTimelineResponse>>> getAll() { return ResponseBuilder.success("Retrieved all research timelines", service.getAllResearchTimelines(), HttpStatus.OK); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<ResearchTimelineResponse>> update(@PathVariable Long id, @Valid @RequestBody ResearchTimelineRequest request) { return ResponseBuilder.success("Research timeline updated successfully", service.updateResearchTimeline(id, request), HttpStatus.OK); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.deleteResearchTimeline(id); return ResponseBuilder.success("Research timeline deleted successfully", HttpStatus.OK); }
}
