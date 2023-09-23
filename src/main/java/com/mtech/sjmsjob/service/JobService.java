package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;

public interface JobService {
    JobListingDto listJobs(int index, int pageSize, String[] sort);

    JobSummaryDto retrieveJob(long id);
}
