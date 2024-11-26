package com.sensor.agri.smartagriculturebackend.core.sensorType.service;

import com.sensor.agri.smartagriculturebackend.core.sensorType.entity.SensorType;
import com.sensor.agri.smartagriculturebackend.core.sensorType.repository.SensorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorTypeService {

    private final SensorTypeRepository sensorTypeRepository;

    // Save a new Sensor Type
    public void saveSensorType(SensorType sensorType) {
        sensorTypeRepository.save(sensorType);
    }

    // Get all Sensor Types
    public List<SensorType> getAllSensorTypes() {
        return sensorTypeRepository.findAll();
    }

    // Get a SensorType by ID
    public Optional<SensorType> getSensorTypeById(int id) {
        return sensorTypeRepository.findById(id);
    }

    // Update an existing SensorType
    public void updateSensorType(int id, SensorType sensorType) {
        Optional<SensorType> existingSensorType = sensorTypeRepository.findById(id);
        if (existingSensorType.isPresent()) {
            SensorType updatedSensorType = existingSensorType.get();
            updatedSensorType.setName(sensorType.getName());
            updatedSensorType.setDescription(sensorType.getDescription());
            updatedSensorType.setUnit(sensorType.getUnit());
            updatedSensorType.setProtocol(sensorType.getProtocol());
            sensorTypeRepository.save(updatedSensorType);
        }
    }

    // Delete a SensorType
    public void deleteSensorType(int id) {
        Optional<SensorType> sensorType = sensorTypeRepository.findById(id);
        sensorType.ifPresent(sensorTypeRepository::delete);
    }
}
