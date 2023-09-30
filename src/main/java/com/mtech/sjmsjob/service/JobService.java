package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;

import java.math.BigDecimal;

public interface JobService {
    JobListingDto listJobs(int index, int pageSize, String[] sort);

    JobDto retrieveJob(long id);

    //JobListingDto searchJobs(int index, int pageSize, String[] sort, String keyWords, String[] employmentType);
    JobListingDto searchJob(int index, int pageSize, String[] sort, String keyWords, String[] employmentType, String[] workLocations
            , BigDecimal minimumSalary, String[] skills);
    //searchJobListingDto searchJobs(int index, int pageSize, String[] sort, String[] jobTitles);
}
