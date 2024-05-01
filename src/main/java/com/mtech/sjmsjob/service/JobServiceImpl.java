package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.config.CacheConfig;
import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.mappers.JobMapper;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.repository.JobApplicationRepository;
import com.mtech.sjmsjob.repository.JobRepository;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class JobServiceImpl implements JobService {
    private final  JobRepository jobRepository;

    private final JobApplicationRepository jobApplicationRepository;

    private final JobMapper jobMapper = JobMapper.INSTANCE;

    public JobServiceImpl(JobRepository jobRepository, JobApplicationRepository jobApplicationRepository){
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public JobListingDto listJobs(int index, int pageSize, String[] sort) {
        Page<Job> jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
        return JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
    }

    public JobListingDto searchJob(int index, int pageSize, String[] sort, String keyWords, String[] employmentType
            , String[] workLocations, BigDecimal minimumSalary, String[] skills) {
        Page<Job> jobs;
        JobListingDto result = null;

        String employmentTypesConcat =sanitizedString( String.join("|",employmentType));
        String workLocationsConcat = sanitizedString( String.join("|",workLocations));
        String skillsConcat = sanitizedString( String.join("|",skills));

        //sanitized searched word

        //replace spaces with | delimiter for fulltext search
        keyWords = keyWords.trim().replaceAll("\\s+", "|");
        jobs = this.jobRepository.search(keyWords, employmentTypesConcat, workLocationsConcat, minimumSalary, skillsConcat, getPagingRequest(index, pageSize, sort));

        if (jobs != null)
            result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);

        return result;
    }

    private String sanitizedString(String strInput){
        return strInput.replaceAll("[;/%!\\/]", "");
    }
    private Pageable getPagingRequest(int index, int pageSize, String[] sort){
        ArrayList<Sort.Order> sortOrder = new ArrayList<>();
        for (String sortBy : sort) {
            var sortParams = sortBy.split("\\|");
            var sortDirection = Sort.Direction.ASC;
            if(sortParams.length >= 2){
                if(sortParams[1].equalsIgnoreCase(Sort.Direction.DESC.toString()))
                    sortDirection = Sort.Direction.DESC;
            }
            if(sortParams.length >=1) sortBy = sortParams[0];
            sortOrder.add(new Sort.Order(sortDirection,sortBy));
        }
        return PageRequest.of(index,pageSize, Sort.by(sortOrder) );
    }

    public JobDto retrieveJob(long jobId, String userId) {
        log.debug("retrieveJob for jobId={}, userId={}, ", jobId, userId);
        JobDto jobDto = retrieveJob(jobId);
        jobDto.setApplied(false);
        if (jobDto!= null && !StringUtils.isBlank(userId)){
            var applicationListOptional = jobApplicationRepository.findByUserIdAndJobId(UUID.fromString(userId), jobId);
            if(applicationListOptional.isPresent() && !applicationListOptional.get().isEmpty()) {
                jobDto.setApplied(true);
            }
        }
        return jobDto;
    }

    @Cacheable(value = CacheConfig.JOBS, key = "#id")
    public JobDto retrieveJob(long id) {
        log.debug("retrieveJob for jobId={}", id);
        Optional<Job> job = this.jobRepository.findById(id);
        if(job.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id "+ id + "not found");
        }
        return jobMapper.jobToJobDto(job.get());
    }

}
