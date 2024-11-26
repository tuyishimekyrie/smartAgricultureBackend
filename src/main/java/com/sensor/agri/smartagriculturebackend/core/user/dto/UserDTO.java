package com.sensor.agri.smartagriculturebackend.core.user.dto;


import com.sensor.agri.smartagriculturebackend.core.user.enums.ERole;
import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String password;
    private ERole role;
}
