package com.alex.researchhub.service.notification;
import java.util.List;
import com.alex.researchhub.dto.notification.*;
public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest data);
    NotificationResponse getNotification(Long id);
    List<NotificationResponse> getAllNotifications();
    NotificationResponse updateNotification(Long id, NotificationRequest data);
    void deleteNotification(Long id);
}
