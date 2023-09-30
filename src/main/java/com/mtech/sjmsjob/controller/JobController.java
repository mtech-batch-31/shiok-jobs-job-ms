package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jobs")
public class JobController
{
    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> retrieveJob(@PathVariable long id) {
        JobDto jobSummaryDto = jobService.retrieveJob(id);
        return ResponseEntity.ok(jobSummaryDto);
    }

    //search criteria
    //?keywords=Software Engineer
    //fulltext search for job title, company, job summary, skill
    //defined search for salary range, employee type, location
    @GetMapping()

    public ResponseEntity<JobListingDto> Search(@RequestParam(defaultValue = "0") int index,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(defaultValue = "posted_date,desc") String[] sort,
                                                @RequestParam(defaultValue = "") String keywords){
        var result = this.jobService.searchJobs(index, pageSize, sort, keywords);
        return ResponseEntity.ok(result);
    }
}
