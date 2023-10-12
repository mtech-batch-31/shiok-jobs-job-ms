package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import java.util.HashMap;
import java.util.Map;

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
                                                @RequestParam(defaultValue = "posted_date|desc") String[] sort,
                                                @RequestParam(defaultValue = "") String keywords,
                                                @RequestParam(defaultValue = "") String[] employmentType,
                                                @RequestParam(defaultValue = "") String[] workLocations,
                                                @RequestParam(defaultValue = "") String minimumSalary,
                                                @RequestParam(defaultValue = "") String[] skills){

        var pattern = Pattern.compile("^[\\d]*[\\.]?[\\d]*$");
        BigDecimal minSalary = BigDecimal.ZERO;
        if(!minimumSalary.isEmpty() && pattern.matcher(minimumSalary).matches())
            minSalary = new BigDecimal(minimumSalary);
        var result = this.jobService.searchJob(index, pageSize, sort, keywords, employmentType, workLocations, minSalary, skills);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> Testing() {
        Map<String, String> greeting = new HashMap<>();
        greeting.put("greeting", "Hello World from Jobs Microservice");
        return ResponseEntity.ok(greeting);
    }
}
