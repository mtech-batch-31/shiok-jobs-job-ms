package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.mappers.JobMapper;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    final private JobRepository jobRepository;

    private final JobMapper jobMapper = JobMapper.INSTANCE;

    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public JobListingDto listJobs(int index, int pageSize, String[] sort) {

        Page<Job> jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
        return JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
    }

    public JobListingDto searchJobs(int index, int pageSize, String[] sort, String[] jobTitles){
        Page<Job> jobs;
        JobListingDto result = null;
        if(jobTitles.length > 0){
            //jobs = this.jobRepository.search(jobTitles, getPagingRequest(index,pageSize, sort ));
            return null;
       }
       else {
            jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
       }
        result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
        return result;
    }

    public JobListingDto searchJobs(int index, int pageSize, String[] sort, String keyWords){
        Page<Job> jobs;
        JobListingDto result = null;
        if(keyWords.length() > 0){
            //form search terms (simple version)
            keyWords = keyWords.trim().replaceAll("\\s+", "|")           ;
            jobs = this.jobRepository.search(keyWords, getPagingRequest(index,pageSize, sort ));
        }
        else {
            jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
        }
        if(jobs != null)
            result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
        return result;
    }

    private Pageable getPagingRequest(int index, int pageSize, String[] sort){
        ArrayList<Sort.Order> sortOrder = new ArrayList<>();
        for (String sortBy : sort) {
            sortOrder.add(new Sort.Order(Sort.Direction.ASC,sortBy));
        }
        Pageable paging = PageRequest.of(index,pageSize, Sort.by(sortOrder) );
        Page<Job> jobs = this.jobRepository.findAll(paging);
        ArrayList<JobSummaryDto> joblist = new ArrayList<>();

        for (Job job: jobs) {
            joblist.add(JobMapper.INSTANCE.jobToJobSummaryDto(job));
        }
        JobListingDto result = new JobListingDto();
        result.setIndex(index);
        result.setPageSize(pageSize);
        result.setTotalRecord(jobs.getTotalElements());
        result.setData(joblist);

        return result;
    }

    public JobDto retrieveJob(long id) {
        Optional<Job> job = this.jobRepository.findById(id);
        if(job.isEmpty()) {
            return null;
        }
        return jobMapper.jobToJobDto(job.get());
    }

}
