package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobApplyRequest;
import com.mtech.sjmsjob.service.JobApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
public class JobApplicationController {

    private final JobApplicationService jobApplService;
    public JobApplicationController(JobApplicationService jobApplService){
        this.jobApplService = jobApplService;
    }
    @PostMapping("/apply")
    public ResponseEntity applyJob(@RequestHeader("user-id") String accountUuid, @Validated @RequestBody JobApplyRequest jobRequest) throws IllegalArgumentException {

        long jobId = jobRequest.getId();
        try
        {
            Pattern UUID_REGEX =
                    Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

            if(jobId <= 0)
                return ResponseEntity.badRequest().body("Missing or Invalid Job Id");
            if(accountUuid.isEmpty() || !UUID_REGEX.matcher(accountUuid).matches())
                return ResponseEntity.badRequest().body("Missing or Invalid User Id");

            jobApplService.applyJob(UUID.fromString(accountUuid), jobId);
        } catch(Exception ex)
        {
//            ex.printStackTrace();
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(false);
        }
        return ResponseEntity.ok("Job applied successfully");
    }
}
