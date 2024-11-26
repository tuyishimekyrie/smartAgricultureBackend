package com.sensor.agri.smartagriculturebackend.core.sensor.entity;

import com.fasterxml.jackson.annotation.*;
import com.sensor.agri.smartagriculturebackend.core.sensor.enums.ESensorStatus;
import com.sensor.agri.smartagriculturebackend.core.sensorType.entity.SensorType;
import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import com.sensor.agri.smartagriculturebackend.core.user.enums.ERole;
import com.sensor.agri.smartagriculturebackend.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sensor extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ESensorStatus sensorStatus;

    // A sensor belongs to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    @JsonIgnore
    private User user;


    // A sensor has one sensor type
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type_id", referencedColumnName = "id")
//    @JsonManagedReference
    @JsonIgnore
    private SensorType sensorType;
}
