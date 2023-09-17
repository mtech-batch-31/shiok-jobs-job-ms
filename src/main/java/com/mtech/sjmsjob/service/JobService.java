package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;

public interface JobService {
    public Iterable<JobSummaryDto> listJobs();
}
