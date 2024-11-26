package com.sensor.agri.smartagriculturebackend.controller.sensortype;

import com.sensor.agri.smartagriculturebackend.core.sensorType.entity.SensorType;
import com.sensor.agri.smartagriculturebackend.core.sensorType.service.SensorTypeService;
import com.sensor.agri.smartagriculturebackend.security.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor-type")
@RequiredArgsConstructor
public class SensorTypeController {

    private final SensorTypeService sensorTypeService;

    // Create a new Sensor Type
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createSensorType(@RequestBody SensorType sensorType) {
        try {
            sensorTypeService.saveSensorType(sensorType);
            ApiResponse<String> response = ApiResponse.success("SensorType created successfully", "SensorType created.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.failure("Error creating SensorType", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get all Sensor Types
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SensorType>>> getAllSensorTypes() {
        try {
            List<SensorType> sensorTypes = sensorTypeService.getAllSensorTypes();
            ApiResponse<List<SensorType>> response = ApiResponse.success("Fetched all SensorTypes", sensorTypes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<SensorType>> response = ApiResponse.failure("Error fetching SensorTypes", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get a SensorType by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SensorType>> getSensorTypeById(@PathVariable int id) {
        try {
            Optional<SensorType> sensorTypeOptional = sensorTypeService.getSensorTypeById(id);

            if (sensorTypeOptional.isPresent()) {
                // If SensorType is found, return success with the data
                ApiResponse<SensorType> response = ApiResponse.success("Fetched SensorType by ID", sensorTypeOptional.get());
                return ResponseEntity.ok(response);
            } else {
                // If SensorType is not found, return failure response
                ApiResponse<SensorType> response = ApiResponse.failure("SensorType not found", "NOT_FOUND", "No SensorType found for the provided ID.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // Return a failure response if an exception occurs
            ApiResponse<SensorType> response = ApiResponse.failure("Error fetching SensorType", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // Update a SensorType
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateSensorType(@PathVariable int id, @RequestBody SensorType sensorType) {
        try {
            sensorTypeService.updateSensorType(id, sensorType);
            ApiResponse<String> response = ApiResponse.success("SensorType updated successfully", "SensorType updated.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.failure("Error updating SensorType", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Delete a SensorType
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSensorType(@PathVariable int id) {
        try {
            sensorTypeService.deleteSensorType(id);
            ApiResponse<String> response = ApiResponse.success("SensorType deleted successfully", "SensorType deleted.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.failure("Error deleting SensorType", "INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
