package com.mtech.sjmsjob.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/jobs")
public class HealthController {

    @RequestMapping(value = "/actuator/health", method = RequestMethod.GET)
    public String healthCheck() {
        return "OK";
    }
}
