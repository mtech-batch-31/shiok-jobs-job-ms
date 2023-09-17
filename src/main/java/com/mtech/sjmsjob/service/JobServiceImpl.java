package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.mappers.JobMapper;
import com.mtech.sjmsjob.model.JobListingDto;
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

    public Iterable<JobSummaryDto> listJobs() {
        var jobs = this.jobRepository.findAll();
        ArrayList<JobSummaryDto> joblist = new ArrayList<JobSummaryDto>();

        for (Job job: jobs) {
            joblist.add(JobMapper.INSTANCE.jobToJobSummaryDto(job));
        }
        JobListingDto result = new JobListingDto();
        result.setIndex(0);
        result.setPageSize(20);
        result.setTotalRecord(joblist.size());
        result.setData(joblist);

        return joblist;
    }
}
