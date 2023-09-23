package com.mtech.sjmsjob.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.mtech.sjmsjob.entity.Job;

import java.util.Optional;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

    Optional<Job> findById(Long id);
}
