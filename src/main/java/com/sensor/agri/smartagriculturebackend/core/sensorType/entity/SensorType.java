package com.sensor.agri.smartagriculturebackend.core.sensorType.entity;

import com.fasterxml.jackson.annotation.*;
import com.sensor.agri.smartagriculturebackend.core.sensor.entity.Sensor;
import com.sensor.agri.smartagriculturebackend.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sensor_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SensorType extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    private String unit;

    private String protocol;

    // A sensor type can belong to multiple sensors
    @OneToMany(mappedBy = "sensorType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonBackReference
    @JsonIgnore
    private List<Sensor> sensors;
}
