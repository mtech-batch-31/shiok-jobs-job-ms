package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobApplyDto;
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

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
public class JobApplicationController {

    private final JobApplicationService jobApplService;
    public JobApplicationController(JobApplicationService jobApplService){
        this.jobApplService = jobApplService;
    }
    @PostMapping("/apply")
    public ResponseEntity<JobApplyDto> applyJob(@RequestHeader("user-id") String userId, @Validated @RequestBody JobApplyRequest jobRequest) throws IllegalArgumentException {

        long jobId = jobRequest.getId();
        try
        {
//            Pattern UUID_REGEX =
//                    Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
//            Pattern UUID_COGNITO_REGEX =
//                    Pattern.compile("^[a-zA-F]{6}_[0-9]{21}$");

            if(jobId <= 0)
                return ResponseEntity.badRequest().body(JobApplyDto.builder().status("Fail").message("Missing or Invalid Job Id").build());
            if(userId.isBlank())
                return ResponseEntity.badRequest().body(JobApplyDto.builder().status("Fail").message("Missing or Invalid User Id").build());

            jobApplService.applyJob(userId, jobId);
        } catch(Exception ex)
        {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(JobApplyDto.builder().status("Fail").message(ex.getMessage()).build());
        }
        return ResponseEntity.ok(JobApplyDto.builder().status("Success").message("Job Application Successful").build());
    }
}
