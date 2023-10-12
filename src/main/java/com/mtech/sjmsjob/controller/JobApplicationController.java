package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobApplicationService;
import com.mtech.sjmsjob.util.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/jobappl")
public class JobApplicationController {

    private final JobApplicationService jobApplService;
    public JobApplicationController(JobApplicationService jobApplService){
        this.jobApplService = jobApplService;
    }
    @PostMapping("/apply")
    public ResponseEntity applyJob(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody String jobId) throws IllegalArgumentException {
        try
        {
            if(token.isEmpty())
                return ResponseEntity.badRequest().body("Unknown user");
            var username = new JwtTokenUtil().getUserNameFromJWT(token);
            if(username.isEmpty())
                return ResponseEntity.badRequest().body("Unknown user");
            long jobIdval = Long.valueOf(jobId);
            jobApplService.applyJob(UUID.fromString(username), jobIdval);
        } catch(Exception ex)
        {
            return ResponseEntity.internalServerError().body(false);
        }
        return ResponseEntity.ok("Job applied successfully");
    }
}
