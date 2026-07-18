package com.alex.researchhub.controller;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.alex.researchhub.common.response.*;
import com.alex.researchhub.dto.notification.*;
import com.alex.researchhub.service.notification.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController @RequestMapping("/api/notification") @RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;
    @PostMapping public ResponseEntity<ApiResponse<NotificationResponse>> create(@Valid @RequestBody NotificationRequest request) { return ResponseBuilder.success("Notification created successfully", service.createNotification(request), HttpStatus.CREATED); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<NotificationResponse>> get(@PathVariable Long id) { return ResponseBuilder.success("Notification retrieved successfully", service.getNotification(id), HttpStatus.OK); }
    @GetMapping public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll() { return ResponseBuilder.success("Retrieved all notifications", service.getAllNotifications(), HttpStatus.OK); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<NotificationResponse>> update(@PathVariable Long id, @Valid @RequestBody NotificationRequest request) { return ResponseBuilder.success("Notification updated successfully", service.updateNotification(id, request), HttpStatus.OK); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.deleteNotification(id); return ResponseBuilder.success("Notification deleted successfully", HttpStatus.OK); }
}
