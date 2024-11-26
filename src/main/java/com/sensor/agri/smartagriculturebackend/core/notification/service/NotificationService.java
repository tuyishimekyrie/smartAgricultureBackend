package com.sensor.agri.smartagriculturebackend.core.notification.service;


import com.sensor.agri.smartagriculturebackend.core.notification.entity.Notification;
import com.sensor.agri.smartagriculturebackend.core.notification.enums.NotificationStatus;
import com.sensor.agri.smartagriculturebackend.core.notification.repository.NotificationRepository;
import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Send a notification
    public Notification sendNotification(int userId, String name, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setName(name);
        notification.setMessage(message);
        notification.setNotificationStatus(NotificationStatus.UNREAD);

        notification.setSentAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    // Get all notifications for a specific user
    public List<Notification> getNotificationsForUser(int userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Mark notification as read
    public Notification markAsRead(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setNotificationStatus(NotificationStatus.READ);
        return notificationRepository.save(notification);
    }

    // Get unread notifications for a specific user
    public List<Notification> getUnreadNotifications(int userId) {
        return notificationRepository.findByUserIdAndNotificationStatus(userId, NotificationStatus.UNREAD);
    }
}
