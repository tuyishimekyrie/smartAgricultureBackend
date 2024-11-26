package com.sensor.agri.smartagriculturebackend.controller.notification;

import com.sensor.agri.smartagriculturebackend.controller.notification.dto.NotificationRequest;
import com.sensor.agri.smartagriculturebackend.core.notification.entity.Notification;
import com.sensor.agri.smartagriculturebackend.core.notification.service.NotificationService;
import com.sensor.agri.smartagriculturebackend.security.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Endpoint to send a notification
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Notification>> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            // Send notification using the values from the request body
            Notification notification = notificationService.sendNotification(notificationRequest.getUserId(),
                    notificationRequest.getName(),
                    notificationRequest.getMessage());
            ApiResponse<Notification> response = ApiResponse.success("Notification sent successfully", notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Notification> response = ApiResponse.failure("Error sending notification", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get all notifications for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotificationsForUser(@PathVariable int userId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsForUser(userId);
            if (notifications.isEmpty()) {
                ApiResponse<List<Notification>> response = ApiResponse.failure("No notifications found for user", "NOT_FOUND", "The user has no notifications.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse<List<Notification>> response = ApiResponse.success("Fetched notifications for user", notifications);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Notification>> response = ApiResponse.failure("Error fetching notifications", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Mark a notification as read
    @PatchMapping("/markAsRead/{notificationId}")
    public ResponseEntity<ApiResponse<Notification>> markNotificationAsRead(@PathVariable int notificationId) {
        try {
            Notification notification = notificationService.markAsRead(notificationId);
            ApiResponse<Notification> response = ApiResponse.success("Notification marked as read", notification);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Notification> response = ApiResponse.failure("Error marking notification as read", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get unread notifications for a specific user
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<Notification>>> getUnreadNotifications(@PathVariable int userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            if (notifications.isEmpty()) {
                ApiResponse<List<Notification>> response = ApiResponse.failure("No unread notifications found for user", "NOT_FOUND", "The user has no unread notifications.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse<List<Notification>> response = ApiResponse.success("Fetched unread notifications for user", notifications);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Notification>> response = ApiResponse.failure("Error fetching unread notifications", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
