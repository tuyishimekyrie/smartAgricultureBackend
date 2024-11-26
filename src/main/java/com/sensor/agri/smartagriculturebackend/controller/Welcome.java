package com.sensor.agri.smartagriculturebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class Welcome {
    @GetMapping("/")
    public String greeting() {
        return "Welcome to SmartAgriculture backend";
    }
}
