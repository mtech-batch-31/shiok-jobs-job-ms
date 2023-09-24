package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController
{
    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<JobListingDto> listJobs(@RequestParam(defaultValue = "0") int index,
                                                  @RequestParam(defaultValue = "10") int pagesize,
                                                  @RequestParam(defaultValue = "id") String[] sort)
    {
        JobListingDto jobList = this.jobService.listJobs(index,pagesize, sort);

        return ResponseEntity.ok(jobList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> retrieveJob(@PathVariable long id) {
        JobDto jobSummaryDto = jobService.retrieveJob(id);
        return ResponseEntity.ok(jobSummaryDto);
    }

    //search criteria
    //job title, salary range, company, employee type, currency, location,
    // skill (multiple skills)
    //?jobtitle="senior developer"&jobtitle="senior architect"&skill=""
    @GetMapping("/search")

    public ResponseEntity<JobListingDto> Search(@RequestParam(defaultValue = "0") int index,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(defaultValue = "id") String[] sort,
                                                @RequestParam(defaultValue = "") String keyWords){
        var result = this.jobService.searchJobs(index, pageSize, sort, keyWords);
        return ResponseEntity.ok(result);
    }
}
