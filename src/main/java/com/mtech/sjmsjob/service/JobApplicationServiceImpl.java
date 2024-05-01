package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.JobApplication;
import com.mtech.sjmsjob.repository.JobApplicationRepository;
import com.mtech.sjmsjob.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class JobApplicationServiceImpl implements JobApplicationService{
    private final JobApplicationRepository jobApplRepository;

    private final JobRepository jobRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, JobRepository jobRepository){
        this.jobApplRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
    }
    public JobApplication applyJob(String userId, long jobId) {
        var job = jobRepository.findById(jobId);
        if(!job.isPresent())
        {
            throw new IllegalArgumentException("invalid job id");
        }
        else {
            var applied = jobApplRepository.findByUserIdAndJobId(userId, jobId);
            if(applied.isPresent() && applied.get().size() > 0)
            {
                return applied.get().get(0);
            }
            var now = new Date();
            JobApplication jobAppl = new JobApplication();
            jobAppl.setJob(job.get());
            jobAppl.setUserId(userId);
            jobAppl.setAppliedDate(now);
            jobAppl.setStatus("Applied");
            jobAppl.setVersion(1);
            jobAppl.setCreatedBy(userId);
            jobAppl.setCreatedTime(now);
            jobAppl.setLastUpdatedBy(userId);
            jobAppl.setLastUpdatedTime(new Timestamp(now.getTime()));
            jobAppl.setSeekerStatus(true);
            jobAppl.setSeekerStatusLastUpdatedDate(new Timestamp(now.getTime()));

            return jobApplRepository.save(jobAppl);
        }
    }
}
