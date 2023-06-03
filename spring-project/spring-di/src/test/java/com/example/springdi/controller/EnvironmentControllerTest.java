package com.example.springdi.controller;

import com.example.springdi.service.EnvironmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("uat")
@SpringBootTest
class EnvironmentControllerTest {

    @Autowired
    private EnvironmentController environmentController;

    @Test
    void getProfile() {
        System.out.println(" TEST PROFILE - "+environmentController.getProfile());
    }
}