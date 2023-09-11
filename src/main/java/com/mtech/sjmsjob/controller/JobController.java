package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobSummaryDto;
import com.mtech.sjmsjob.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController
{
    private JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }
    @GetMapping
    public ResponseEntity<Iterable<Job>> listJobs()
    {
        Iterable<Job> result = this.jobService.listJobs();
        return ResponseEntity.ok(result);
    }
}
