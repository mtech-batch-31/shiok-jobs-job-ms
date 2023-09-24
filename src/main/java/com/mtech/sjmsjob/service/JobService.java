package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;

public interface JobService {
    JobListingDto listJobs(int index, int pageSize, String[] sort);

    JobDto retrieveJob(long id);

    JobListingDto searchJobs(int index, int pageSize, String[] sort, String keyWords);

    JobListingDto searchJobs(int index, int pageSize, String[] sort, String[] jobTitles);
}
