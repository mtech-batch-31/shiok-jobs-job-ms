package com.mtech.sjmsjob.repository;

import org.springframework.data.repository.CrudRepository;
import com.mtech.sjmsjob.entity.Job;
public interface JobRepository extends CrudRepository<Job, Long> { }
