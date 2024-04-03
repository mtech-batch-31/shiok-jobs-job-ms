package com.mtech.sjmsjob.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jobs")
public class HealthController {

    @RequestMapping("/actuator/health")
    public String healthCheck() {
        return "OK";
    }
}
