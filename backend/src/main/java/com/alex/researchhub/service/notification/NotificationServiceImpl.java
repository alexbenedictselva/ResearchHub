package com.alex.researchhub.service.notification;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alex.researchhub.dto.notification.*;
import com.alex.researchhub.entity.*;
import com.alex.researchhub.mapper.NotificationMapper;
import com.alex.researchhub.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final ResearcherRepository researcherRepository;
    public NotificationResponse createNotification(NotificationRequest data) {
        Notification notification = NotificationMapper.toEntity(data);
        notification.setResearcher(researcher(data.getResearcher()));
        return NotificationMapper.toResponse(notificationRepository.save(notification));
    }
    public NotificationResponse getNotification(Long id) { return NotificationMapper.toResponse(notification(id)); }
    public List<NotificationResponse> getAllNotifications() { return notificationRepository.findAll().stream().map(NotificationMapper::toResponse).toList(); }
    public NotificationResponse updateNotification(Long id, NotificationRequest data) {
        Notification notification = notification(id);
        notification.setTitle(data.getTitle()); notification.setMessage(data.getMessage()); notification.setType(data.getType());
        notification.setIsRead(data.getIsRead() != null ? data.getIsRead() : notification.getIsRead());
        notification.setCreatedAt(data.getCreatedAt() != null ? data.getCreatedAt() : notification.getCreatedAt());
        notification.setResearcher(researcher(data.getResearcher()));
        return NotificationMapper.toResponse(notificationRepository.save(notification));
    }
    public void deleteNotification(Long id) { notificationRepository.delete(notification(id)); }
    private Notification notification(Long id) { return notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Notification not found")); }
    private Researcher researcher(Long id) { return researcherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Researcher not found")); }
}
