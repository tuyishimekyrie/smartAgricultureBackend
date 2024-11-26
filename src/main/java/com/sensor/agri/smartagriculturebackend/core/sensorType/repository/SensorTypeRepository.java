package com.sensor.agri.smartagriculturebackend.core.sensorType.repository;

import com.sensor.agri.smartagriculturebackend.core.sensorType.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Integer> {

}
