package com.alex.researchhub.controller;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.alex.researchhub.common.response.*;
import com.alex.researchhub.dto.savedpaper.*;
import com.alex.researchhub.service.savedpaper.SavedPaperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController @RequestMapping("/api/saved-paper") @RequiredArgsConstructor
public class SavedPaperController {
    private final SavedPaperService service;
    @PostMapping public ResponseEntity<ApiResponse<SavedPaperResponse>> create(@Valid @RequestBody SavedPaperRequest request) { return ResponseBuilder.success("Saved paper created successfully", service.createSavedPaper(request), HttpStatus.CREATED); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<SavedPaperResponse>> get(@PathVariable Long id) { return ResponseBuilder.success("Saved paper retrieved successfully", service.getSavedPaper(id), HttpStatus.OK); }
    @GetMapping public ResponseEntity<ApiResponse<List<SavedPaperResponse>>> getAll() { return ResponseBuilder.success("Retrieved all saved papers", service.getAllSavedPapers(), HttpStatus.OK); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<SavedPaperResponse>> update(@PathVariable Long id, @Valid @RequestBody SavedPaperRequest request) { return ResponseBuilder.success("Saved paper updated successfully", service.updateSavedPaper(id, request), HttpStatus.OK); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.deleteSavedPaper(id); return ResponseBuilder.success("Saved paper deleted successfully", HttpStatus.OK); }
}
