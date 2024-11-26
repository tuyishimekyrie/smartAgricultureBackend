package com.sensor.agri.smartagriculturebackend.core.user.service;

import com.sensor.agri.smartagriculturebackend.core.resetToken.entity.ResetToken;
import com.sensor.agri.smartagriculturebackend.core.resetToken.repository.ResetTokenRepository;
import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.enums.ERole;
import com.sensor.agri.smartagriculturebackend.core.user.repository.UserRepository;
import com.sensor.agri.smartagriculturebackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Initialize encoder

    // In-memory token storage (use a database or cache for production)
//    private final Map<String, String> resetTokenStorage = new HashMap<>();
    private final ResetTokenRepository resetTokenRepository;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public String createUser(String firstname, String lastname, String username, String email, String phone, String rawPassword, ERole role) {
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Create a new user object
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(hashedPassword);
        user.setRole(role);

        userRepository.save(user);
        return "User saved successfully";
    }

    public String verify(User user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            logger.info("Authenticated user: {}", user.getEmail());
            return jwtService.generateToken(user);
        }
        return "Authentication failed";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String generatePasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!Objects.isNull(userOptional)) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); // Token valid for 1 hour

            // Save the reset token with the user
            ResetToken resetToken = new ResetToken(token, user, expiryDate);
            resetTokenRepository.save(resetToken); // Ensure the user is linked correctly

            logger.info("Generated reset token for user {}: {}", email, token); // For debugging
            return token;
        }
        logger.warn("User not found: {}", email);
        return null;
    }


    public boolean resetPassword(String token, String newPassword) {
        Optional<ResetToken> tokenOptional = resetTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            ResetToken resetToken = tokenOptional.get();

            // Check token expiry
            if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                logger.warn("Token expired: {}", token);
                resetTokenRepository.delete(resetToken);
                return false;
            }

            User user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // Remove the used token
            resetTokenRepository.delete(resetToken);

            logger.info("Password reset successful for user: {}", user.getUsername());
            return true;
        }
        logger.warn("Invalid or expired token: {}", token);
        return false;
    }
}
