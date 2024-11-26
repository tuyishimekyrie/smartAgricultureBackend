package com.sensor.agri.smartagriculturebackend.controller.auth;

import com.sensor.agri.smartagriculturebackend.core.user.dto.UserDTO;
import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.service.UserService;
import com.sensor.agri.smartagriculturebackend.security.ApiResponse;
import com.sensor.agri.smartagriculturebackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService tokenProvider;

    @PostMapping("/register")
    public String createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getPassword(), userDTO.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody User user) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            // Generate JWT token
            String jwt = tokenProvider.generateToken(user);

            // Return success response with token
            ApiResponse<Map<String, String>> response = new ApiResponse<>("success", "Login successful", Map.of("token", jwt));
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {

            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Invalid credentials", "BAD_CREDENTIALS", "The provided username or password is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}