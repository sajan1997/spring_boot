package com.example.springdi.controller;

import com.example.springdi.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping("/getProfile")
    public String getProfile(){
        return "Active Profile is " +environmentService.getProfileInfo();
    }

}
