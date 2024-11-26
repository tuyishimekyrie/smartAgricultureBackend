package com.sensor.agri.smartagriculturebackend.core.user.repository;

import com.sensor.agri.smartagriculturebackend.core.user.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
