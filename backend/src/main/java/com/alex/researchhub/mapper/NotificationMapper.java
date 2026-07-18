package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.notification.*;
import com.alex.researchhub.entity.Notification;

public class NotificationMapper {
    public static Notification toEntity(NotificationRequest request) {
        return Notification.builder().id(request.getId()).title(request.getTitle()).message(request.getMessage())
                .type(request.getType()).isRead(request.getIsRead()).createdAt(request.getCreatedAt()).build();
    }
    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder().id(notification.getId()).title(notification.getTitle())
                .message(notification.getMessage()).type(notification.getType()).isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt()).researcher(notification.getResearcher() == null ? null : notification.getResearcher().getId()).build();
    }
}
