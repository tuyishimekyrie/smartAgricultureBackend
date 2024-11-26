package com.sensor.agri.smartagriculturebackend.core.notification.repository;

import com.sensor.agri.smartagriculturebackend.core.notification.entity.Notification;
import com.sensor.agri.smartagriculturebackend.core.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(int userId);

    List<Notification> findByUserIdAndNotificationStatus(int userId, NotificationStatus notificationStatus);

}
