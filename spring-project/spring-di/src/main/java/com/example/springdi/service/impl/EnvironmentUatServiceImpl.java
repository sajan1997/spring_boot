package com.example.springdi.service.impl;

import com.example.springdi.service.EnvironmentService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("uat")
@Service
public class EnvironmentUatServiceImpl implements EnvironmentService {

    @Override
    public String getProfileInfo() {
        return "UAT";
    }

}
