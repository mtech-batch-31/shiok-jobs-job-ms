package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import com.mtech.sjmsjob.service.JobService;
import jakarta.persistence.Index;
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
//    @GetMapping
//    public ResponseEntity<Iterable<JobSummaryDto>> listJobs()
//    {
//        Iterable<JobSummaryDto> result = this.jobService.listJobs();
//        return ResponseEntity.ok(result);
//    }

    @GetMapping
    public ResponseEntity<JobListingDto> listJobs()
    {
        Iterable<JobSummaryDto> joblist = this.jobService.listJobs();
        JobListingDto result = new JobListingDto();
        result.setIndex(0);
        result.setTotalRecord(0);
        result.setData(joblist);
        result.setPageSize(20);

        return ResponseEntity.ok(result);
    }
}
