package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.repository.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {


    @InjectMocks
    private JobServiceImpl jobServiceImpl;

    @Mock
    private JobRepository jobRepository;

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


    @Test
    void givenId_ReturnJob() {
        // Given
        Mockito.when(jobRepository.findById(1L)).thenReturn(Optional.of(new Job()));

        // When
        JobDto jobDto = jobServiceImpl.retrieveJob(1L );

        // Then
        Assertions.assertNotNull(jobDto);
    }
}
