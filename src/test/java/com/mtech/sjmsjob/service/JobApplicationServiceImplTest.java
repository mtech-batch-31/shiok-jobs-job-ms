package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.entity.JobApplication;
import com.mtech.sjmsjob.repository.JobApplicationRepository;
import com.mtech.sjmsjob.repository.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class JobApplicationServiceImplTest {

    @Mock
    private JobApplicationRepository jobApplyRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @BeforeEach
    public void init() {
        jobRepository = Mockito.mock(JobRepository.class);
        jobApplyRepository = Mockito.mock(JobApplicationRepository.class);
        jobApplicationService = new JobApplicationServiceImpl(jobApplyRepository, jobRepository);
    }

    @Test
    void givenJobSeekersApplyJob_ReturnAppliedJob() {
        //given
        var userId = UUID.randomUUID();
        long jobId = 1L;
        var job = new Job();
        var jobApplications = new ArrayList<JobApplication>();
        jobApplications.add(new JobApplication());

        //when
        Mockito.when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        Mockito.when(jobApplyRepository.findByUserIdAndJobId(userId, jobId)).thenReturn(Optional.of(jobApplications));

        JobApplication jobApplication = jobApplicationService.applyJob(userId, jobId);

        //then
        Assertions.assertNotNull(jobApplication);
    }

    @Test
    void givenJobSeekersApplyJob_SaveToDatabaseAndReturnAppliedJob() {
        //given
        var userId = UUID.randomUUID();
        long jobId = 1L;
        var job = new Job();
        job.setJobTitle("job title");
        var jobApplications = new ArrayList<JobApplication>();

        //when
        Mockito.when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        Mockito.when(jobApplyRepository.findByUserIdAndJobId(userId, jobId)).thenReturn(Optional.of(jobApplications));
        Mockito.when(jobApplyRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        JobApplication jobApplication = jobApplicationService.applyJob(userId, jobId);

        //then
        Assertions.assertNotNull(jobApplication);
        Assertions.assertEquals("job title", jobApplication.getJob().getJobTitle());
        Assertions.assertEquals(userId, jobApplication.getUserId());
        Assertions.assertEquals("Applied", jobApplication.getStatus());
        Assertions.assertEquals(1, jobApplication.getVersion());
        Assertions.assertEquals(userId.toString(), jobApplication.getCreatedBy());
        Assertions.assertEquals(userId.toString(), jobApplication.getLastUpdatedBy());
        Assertions.assertTrue(jobApplication.getSeekerStatus());

        Mockito.verify(jobApplyRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void givenJobSeekersApplyJobAndJobDoesNotExist_ThrowIllegalArgumentException() {
        //given
        var jobApplications = new ArrayList<JobApplication>();
        var userId = UUID.randomUUID();
        long jobId = 1L;

        //when
        Mockito.when(jobRepository.findById(jobId)).thenReturn(Optional.empty());
        Mockito.when(jobApplyRepository.findByUserIdAndJobId(userId, jobId)).thenReturn(Optional.of(jobApplications));

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> jobApplicationService.applyJob(userId, jobId));
    }
}
