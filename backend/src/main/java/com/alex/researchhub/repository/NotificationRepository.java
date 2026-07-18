package com.alex.researchhub.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alex.researchhub.entity.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Long> {}
