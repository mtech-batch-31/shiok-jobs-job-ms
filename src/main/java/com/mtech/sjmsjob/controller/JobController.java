package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.service.JobService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
public class JobController
{
    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> retrieveJobById(@RequestHeader HttpHeaders headers, @PathVariable long id) {
        log.info("retrieveJobById, headers={}, id={}",headers,id);
        JobDto jobDto = jobService.retrieveJob(id);
        return ResponseEntity.ok(jobDto);
    }

    @GetMapping("/auth/{id}")
    public ResponseEntity<JobDto> retrieveJobByIdAuthenticated(@RequestHeader HttpHeaders headers, @PathVariable long id) {
        log.info("retrieveJobByIdAuthenticated, headers={}, id={}",headers, id);
        String userId = headers.getFirst("user-id");
        JobDto jobDto = null;
        if (StringUtils.isBlank(userId)){
            log.error("user-id header not found");
            jobDto = jobService.retrieveJob(id);
        } else {
            jobDto = jobService.retrieveJob(id, userId);
        }
        return ResponseEntity.ok(jobDto);
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
}
