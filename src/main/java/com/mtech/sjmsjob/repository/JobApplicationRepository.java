package com.mtech.sjmsjob.repository;

import com.mtech.sjmsjob.entity.JobApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends CrudRepository<JobApplication, Long> {
    Optional<List<JobApplication>> findByUserIdAndJobId(String userId, long job);
}
