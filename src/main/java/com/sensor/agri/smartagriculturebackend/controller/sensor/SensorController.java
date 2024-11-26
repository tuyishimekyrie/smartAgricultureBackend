package com.sensor.agri.smartagriculturebackend.controller.sensor;

import com.sensor.agri.smartagriculturebackend.core.sensor.dto.PaginatedResponse;
import com.sensor.agri.smartagriculturebackend.core.sensor.entity.Sensor;
import com.sensor.agri.smartagriculturebackend.core.sensor.service.SensorService;
import com.sensor.agri.smartagriculturebackend.core.sensorType.entity.SensorType;
import com.sensor.agri.smartagriculturebackend.core.sensorType.repository.SensorTypeRepository;
import com.sensor.agri.smartagriculturebackend.core.sensorType.service.SensorTypeService;
import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.repository.UserRepository;
import com.sensor.agri.smartagriculturebackend.core.user.service.UserService;
import com.sensor.agri.smartagriculturebackend.security.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;
    private final UserService userService;
    private final SensorTypeService sensorTypeService;
    private final UserRepository userRepository;
    private final SensorTypeRepository sensorTypeRepository;

    @PostMapping("/save")
    public ResponseEntity<String> saveSensor(@RequestBody Sensor sensor) {
        try {
            if (sensor.getUser() == null || sensor.getSensorType() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User and Sensor Type must be provided");
            }

            // Fetch User by ID
            User user = userRepository.findById(sensor.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Fetch SensorType by ID
            SensorType sensorType = sensorTypeRepository.findById(sensor.getSensorType().getId())
                    .orElseThrow(() -> new RuntimeException("SensorType not found"));

            // Set the fetched entities into the sensor object
            sensor.setUser(user);
            sensor.setSensorType(sensorType);

            // Save the sensor
            sensorService.saveSensor(sensor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sensor saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving sensor: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponse<Sensor>> getAllSensors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = sensorService.getAllSensors(pageable);

        PaginatedResponse<Sensor> response = new PaginatedResponse<>();
        response.setContent(sensors.getContent());
        response.setTotalPages(sensors.getTotalPages());
        response.setTotalElements(sensors.getTotalElements());
        response.setSize(sensors.getSize());
        response.setNumber(sensors.getNumber());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Sensor>>> getSensorsByUser(@PathVariable int userId) {
        try {
            List<Sensor> sensors = sensorService.getSensorsByUser(userId);

            if (sensors.isEmpty()) {
                ApiResponse<List<Sensor>> response = ApiResponse.failure("No sensors found for the user", "NOT_FOUND", "The user does not have any sensors.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<List<Sensor>> response = ApiResponse.success("Fetched sensors by user", sensors);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Sensor>> response = ApiResponse.failure("Error fetching sensors", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{sensorId}")
    public ResponseEntity<ApiResponse<String>> deleteSensor(@PathVariable int sensorId) {
        try {
            sensorService.deleteSensor(sensorId);
            ApiResponse<String> response = ApiResponse.success("Sensor deleted successfully", "Sensor with ID: " + sensorId + " has been deleted.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.failure("Error deleting sensor", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/{sensorId}")
    public ResponseEntity<ApiResponse<String>> updateSensor(@PathVariable int sensorId, @RequestBody Sensor sensor) {
        try {
            // Fetch existing sensor by ID
            Sensor existingSensor = sensorService.getSensorById(sensorId)
                    .orElseThrow(() -> new RuntimeException("Sensor not found"));

            // Update the sensor's details
            existingSensor.setName(sensor.getName());
            existingSensor.setLocation(sensor.getLocation());
            existingSensor.setSensorType(sensor.getSensorType());
            existingSensor.setUser(sensor.getUser());

            sensorService.saveSensor(existingSensor);

            ApiResponse<String> response = ApiResponse.success("Sensor updated successfully", "Sensor updated with ID: " + sensorId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.failure("Error updating sensor", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Sensor>> searchSensors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = sensorService.searchSensors(name, location, pageable);
        return ResponseEntity.ok(sensors);
    }
}
