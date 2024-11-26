package com.sensor.agri.smartagriculturebackend.core.sensor.repository;

import com.sensor.agri.smartagriculturebackend.core.sensor.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Page<Sensor> findByNameContainingAndLocationContaining(String name, String location, Pageable pageable);

    List<Sensor> findByUserId(int userId);
}
