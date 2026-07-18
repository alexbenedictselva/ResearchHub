package com.alex.researchhub.dto.notification;

import java.time.LocalDateTime;
import com.alex.researchhub.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {
    private Long id;
    @NotBlank private String title;
    @NotBlank private String message;
    @NotNull private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
    @NotNull private Long researcher;
}
