package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobListingDto;

public interface JobService {
    JobListingDto listJobs(int index, int pageSize, String[] sort);
}
