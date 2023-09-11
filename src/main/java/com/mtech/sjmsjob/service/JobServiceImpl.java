package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.mappers.JobMapper;
import com.mtech.sjmsjob.model.JobSummaryDto;
import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JobServiceImpl implements JobService {
    final private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public Iterable<Job> listJobs() {
        var jobs = this.jobRepository.findAll();
        ArrayList<JobSummaryDto> result = new ArrayList<JobSummaryDto>();

        for (Job job: jobs) {
           result.add(JobMapper.INSTANCE.jobToJobSummaryDto(job));
        }
        return jobs;
    }
}
