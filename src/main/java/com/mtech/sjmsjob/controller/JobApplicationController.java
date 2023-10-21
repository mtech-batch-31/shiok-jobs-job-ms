package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1/jobs")
public class JobApplicationController {

    private final JobApplicationService jobApplService;
    public JobApplicationController(JobApplicationService jobApplService){
        this.jobApplService = jobApplService;
    }
    @PostMapping("/apply")
    public ResponseEntity applyJob(@RequestHeader("user-id") String accountUuid, @RequestBody String jobId) throws IllegalArgumentException {
        try
        {
            Pattern UUID_REGEX =
                    Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

            if(jobId.isEmpty())
                return ResponseEntity.badRequest().body("Invalid Job Id");
            if(!StringUtils.isNumeric(jobId))
                return ResponseEntity.badRequest().body("Invalid Job Id");
            if(accountUuid.isEmpty() && !UUID_REGEX.matcher(accountUuid).matches())
                return ResponseEntity.badRequest().body("Missing or Invalid User Id");
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
