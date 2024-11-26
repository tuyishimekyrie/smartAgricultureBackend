package com.sensor.agri.smartagriculturebackend.controller.user;

import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.service.UserService;
import com.sensor.agri.smartagriculturebackend.security.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> apiResponse;

        if (users.isEmpty()) {
            logger.info("No users found");
            apiResponse = new ApiResponse<>("failure", "No users found", null);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} users", users.size());
        apiResponse = new ApiResponse<>("success", "Users fetched successfully", users);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam String username) {
        logger.info("Received password reset request for username: {}", username);
        String token = userService.generatePasswordResetToken(username);

        ApiResponse<String> apiResponse;

        if (token != null) {
            apiResponse = new ApiResponse<>("success", "Password reset token generated successfully.", token);
            return ResponseEntity.ok(apiResponse);
        } else {
            apiResponse = new ApiResponse<>("failure", "User not found. Please check the username.", null);
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        logger.info("Processing password reset for token: {}", token);
        boolean success = userService.resetPassword(token, newPassword);

        ApiResponse<String> apiResponse;

        if (success) {
            apiResponse = new ApiResponse<>("success", "Password has been reset successfully.", "Password reset successful.");
            return ResponseEntity.ok(apiResponse);
        } else {
            apiResponse = new ApiResponse<>("failure", "Invalid or expired token.", "The provided token is invalid or has expired.");
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }


}
