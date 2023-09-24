package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobListingDto;

public interface JobService {
    JobListingDto listJobs(int index, int pageSize, String[] sort);

    JobListingDto searchJobs(int index, int pageSize, String[] sort, String keyWords);

    JobListingDto searchJobs(int index, int pageSize, String[] sort, String[] jobTitles);
}
