package com.mtech.sjmsjob.repository;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.entity.JobApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobApplicationRepository extends CrudRepository<JobApplication, Long> {
    Optional<JobApplication> findByUserIdAndJobId(UUID userId, Job job);
}
