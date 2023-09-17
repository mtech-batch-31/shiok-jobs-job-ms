package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class JobServiceImplTest {

    private JobService jobService;

    @BeforeEach
    public void init() {

    }

    @Test
    void givenEmptySearchCriteria_ReturnJobList() {
        jobService = Mockito.mock(JobServiceImpl.class);
        Iterable<JobSummaryDto> result = jobService.listJobs();

        Assertions.assertNotNull(result);
    }
}
