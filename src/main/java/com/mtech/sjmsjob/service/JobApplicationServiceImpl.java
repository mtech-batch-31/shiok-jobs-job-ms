package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.JobApplication;
import com.mtech.sjmsjob.repository.JobRepository;
import org.springframework.stereotype.Service;
import com.mtech.sjmsjob.repository.JobApplicationRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class JobApplicationServiceImpl implements JobApplicationService{
    final private JobApplicationRepository jobApplRepository;

    final private JobRepository jobRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, JobRepository jobRepository){
        this.jobApplRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
    }
    public JobApplication applyJob(UUID userId, long jobId) {
        var job = jobRepository.findById(jobId);
        if(!job.isPresent())
        {
            throw new IllegalArgumentException("invalid job id");
        }
        else {
            var applied = jobApplRepository.findByUserIdAndJobId(userId, job.get());
            if(applied.isPresent())
            {
                return applied.get();
            }
            var now = new Date();
            JobApplication jobAppl = new JobApplication();
            jobAppl.setJob(job.get());
            jobAppl.setUserId(userId);
            jobAppl.setAppliedDate(now);
            jobAppl.setStatus("Applied");
            jobAppl.setVersion(1);
            jobAppl.setCreatedBy(userId.toString());
            jobAppl.setCreatedTime(now);
            jobAppl.setLastUpdatedBy(userId.toString());
            jobAppl.setLastUpdatedTime(new Timestamp(now.getTime()));

            return jobApplRepository.save(jobAppl);
        }
    }
}
