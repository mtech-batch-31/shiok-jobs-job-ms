package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.model.JobListingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class JobServiceImplTest {


    @BeforeEach
    public void init() {

    }

    @Test
    void givenEmptySearchCriteria_ReturnJobList() {
        JobService jobService;
        jobService = Mockito.mock(JobServiceImpl.class);
        JobListingDto result = jobService.listJobs(0, 10, new String[]{"id"} );

        //Assertions.assertNotNull(result);
    }
}
