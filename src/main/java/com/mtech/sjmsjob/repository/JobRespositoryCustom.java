package com.mtech.sjmsjob.repository;

import com.mtech.sjmsjob.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface JobRespositoryCustom {
    Page<Job> search(@Param("jobTitles") String[] jobTitles, Pageable pageable);
}
