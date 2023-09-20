package com.mtech.sjmsjob.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.mtech.sjmsjob.entity.Job;
public interface JobRepository extends PagingAndSortingRepository<Job, Long> { }
