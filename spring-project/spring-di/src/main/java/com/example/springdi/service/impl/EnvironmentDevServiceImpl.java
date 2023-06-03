package com.example.springdi.service.impl;

import com.example.springdi.service.EnvironmentService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;



@Profile({"dev","default"})
@Service
public class EnvironmentDevServiceImpl implements EnvironmentService {

    @Override
    public String getProfileInfo() {
        return "dev";
    }

}
