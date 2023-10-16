package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/jobappl")
public class JobApplicationController {

    private final JobApplicationService jobApplService;
    public JobApplicationController(JobApplicationService jobApplService){
        this.jobApplService = jobApplService;
    }
    @PostMapping("/apply")
    public ResponseEntity applyJob(@RequestHeader("user-id") String accountUuid, @RequestBody String jobId) throws IllegalArgumentException {
        try
        {
//            if(token.isEmpty())
//                return ResponseEntity.badRequest().body("Unknown user");
//            var username = new JwtTokenUtil().getUserNameFromJWT(token);
//            if(username.isEmpty())
//                return ResponseEntity.badRequest().body("Unknown user");
            long jobIdval = Long.valueOf(jobId);
            jobApplService.applyJob(UUID.fromString(accountUuid), jobIdval);
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(false);
        }
        return ResponseEntity.ok("Job applied successfully");
    }
}
