package com.sensor.agri.smartagriculturebackend.core.resetToken.repository;


import com.sensor.agri.smartagriculturebackend.core.resetToken.entity.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Integer> {
    Optional<ResetToken> findByToken(String token);
    void deleteByToken(String token);
}
