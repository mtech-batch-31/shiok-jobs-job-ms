package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.service.JobService;
import com.mtech.sjmsjob.util.JwtTokenUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/v1/jobs")
public class JobController
{
    private final JobService jobService;
    private final JwtTokenUtil jwtTokenUtil;

    public JobController(JobService jobService, JwtTokenUtil jwtTokenUtil){
        this.jobService = jobService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> retrieveJobById(@RequestHeader HttpHeaders headers, @PathVariable long id) {
        log.info("retrieveJobById, headers={}, id={}",headers,id);
        JobDto jobDto = jobService.retrieveJob(id);
        return ResponseEntity.ok(jobDto);
    }

    @GetMapping("/auth/{id}")
    public ResponseEntity<JobDto> retrieveJobByIdAuthenticated(@RequestHeader HttpHeaders headers, @PathVariable long id) {
        log.info("retrieveJobByIdAuthenticated, headers={}, id={}",headers,id);
        String authToken = headers.getFirst("Authorization");
        JobDto jobDto = null;
        if (StringUtils.isBlank(authToken)){
            log.error("Authorization header not found");
            jobDto = jobService.retrieveJob(id);
        } else {
            String userId = null;
            try{
                userId = jwtTokenUtil.getUserNameFromJWT(authToken.replace("Bearer ",""));
                jobDto = jobService.retrieveJob(id, userId);
            } catch (Exception e){
                e.printStackTrace();
            }
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
