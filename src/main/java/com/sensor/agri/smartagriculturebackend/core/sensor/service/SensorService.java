package com.sensor.agri.smartagriculturebackend.core.sensor.service;

import com.sensor.agri.smartagriculturebackend.core.sensor.entity.Sensor;
import com.sensor.agri.smartagriculturebackend.core.sensor.repository.SensorRepository;
import com.sensor.agri.smartagriculturebackend.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    public void saveSensor(Sensor sensor) {
        // Retrieve user and sensor type from their respective repositories (assuming the user and sensorType are passed correctly)
        if (sensor.getUser() == null || sensor.getSensorType() == null) {
            System.out.println("Sensor"+sensor.getUser());
            throw new IllegalArgumentException("User and Sensor Type must be provided");
        }

        // Check if the user exists in the database
        if (!userRepository.existsById(sensor.getUser().getId())) {
            throw new IllegalArgumentException("User not found");
        }

        // Save the sensor to the database
        sensorRepository.save(sensor);
    }


    public Page<Sensor> getAllSensors(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }

    public List<Sensor> getSensorsByUser(int userId) {
        return sensorRepository.findByUserId(userId);
    }
    public Optional<Sensor> getSensorById(int sensorId) {
        return sensorRepository.findById(sensorId);
    }


    public void deleteSensor(int sensorId) {
        sensorRepository.deleteById(sensorId);
    }
    public Page<Sensor> searchSensors(String name, String location, Pageable pageable) {
        return sensorRepository.findByNameContainingAndLocationContaining(name, location, pageable);
    }

}
