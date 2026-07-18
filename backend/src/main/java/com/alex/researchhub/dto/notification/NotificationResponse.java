package com.alex.researchhub.dto.notification;

import java.time.LocalDateTime;
import com.alex.researchhub.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Long researcher;
}
