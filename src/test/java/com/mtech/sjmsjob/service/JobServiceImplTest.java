package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.entity.JobApplication;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import com.mtech.sjmsjob.repository.JobApplicationRepository;
import com.mtech.sjmsjob.repository.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;


@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {


    @InjectMocks
    private JobServiceImpl jobServiceImpl;

    @Mock
    private JobRepository jobRepository;
    @Mock
    private JobApplicationRepository jobApplicationRepository;

    private ArrayList<Job> testJobListing;

    @BeforeEach
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 9, 28);
        var postedDate1 = calendar.getTime();
        calendar.set(2023, 9, 29);
        var postedDate2 = calendar.getTime();
        calendar.set(2023, 9, 30);
        var postedDate3 = calendar.getTime();
        calendar.set(2023, 12, 31);
        var closingDate = calendar.getTime();
        testJobListing = new ArrayList<>(){{
            add(new Job(1,1,"companyA","Developer", "engineer", "information technology"
                    , "junior", new ArrayList<>(){{add("microsoft word");}}, "part-time"
                    , "hybrid", "9am - 6pm"
                    , new BigDecimal(10000.00), new BigDecimal(15000.00)
                    , postedDate1, closingDate,1, "test", new Date(), "test", new Date(), null ));

            add(new Job(2,1,"companyB","Technical manager", "manage project"
                    , "information technology", "manager"
                    , new ArrayList<>(){{add("project management"); add("prince");}}, "full-time"
                    , "hybrid", "9am - 6pm"
                    , new BigDecimal(20000.00), new BigDecimal(25000.00)
                    , postedDate2, closingDate,1, "test", new Date(), "test", new Date(), null ));

            add(new Job(3,1,"companyC","Lead Consultant", "lead engineer"
                    , "information technology", "senior"
                    , new ArrayList<>(){{add("java");}}, "part-time", "hybrid"
                    , "9am - 6pm", new BigDecimal(20000.00), new BigDecimal(25000.00)
                    , postedDate3, closingDate,1, "test", new Date(), "test", new Date(), null ));
        }};
    }

    @Test
    void givenEmptySearchCriteria_ReturnJobList() {
        //given
        var jobs = testJobListing;

        //when
        var pageable = getPagingRequest(0,10, new String[]{"id"});
        Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 1));
        JobListingDto result = jobServiceImpl.listJobs(0, 10, new String[]{"id"} );

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());
    }

    @Test
    void givenSortByPostedDateDesc_ReturnJobList(){
        //given
        var jobs = testJobListing;

        //when
        var pageable = getPagingRequest(0,10, new String[]{"posted_date|desc"});
        Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.listJobs(0, 10, new String[]{"posted_date|desc"} );

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());
        var data = result.getData();
        JobSummaryDto record1 = data.iterator().next();
        JobSummaryDto record2 = data.iterator().next();
        JobSummaryDto record3 = data.iterator().next();
        Assertions.assertTrue(record1.getPostedAt().compareTo(record2.getPostedAt()) >= 0);
        Assertions.assertTrue(record2.getPostedAt().compareTo(record3.getPostedAt()) >= 0);
        Assertions.assertEquals(1L, record1.getId());
        Assertions.assertEquals("companyA", record1.getCompany());
        Assertions.assertEquals("companya.svg", record1.getLogo());
        Assertions.assertEquals("Developer", record1.getJobTitle());
        Assertions.assertEquals("10000 - 15000", record1.getSalaryRange());
        Assertions.assertEquals("junior", record1.getLevel());
        Assertions.assertEquals("part-time", record1.getEmploymentType());
        Assertions.assertEquals("hybrid", record1.getLocation());
        Assertions.assertEquals(1, record1.getSkills().size());
        Assertions.assertEquals("microsoft word", record1.getSkills().get(0));
    }

//    @Test
//    void givenSearchByKeyword_ReturnJobList(){
//        //given
//        var jobs = testJobListing;
//        var keyWords = "engineer|consultant";
//        var employmentTypes = new String[]{};
//        //when
//        var pageable = getPagingRequest(0,10, new String[]{"posted_date|desc"});
//        Mockito.when(jobRepository.searchOld(keyWords, pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
//        //Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
//        JobListingDto result = jobServiceImpl.searchJobs(0, 10, new String[]{"posted_date|desc"} , keyWords, employmentTypes);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(3, result.getTotalRecord());
//
//    }

    @Test
    void givenSearchByKeyword_ReturnJobList(){
        //given
        var jobs = testJobListing;
        var keyWords = "engineer|consultant";
        var employmentTypes = new String[]{};
        var workLocations = new String[]{};
        var minimumSalary = new BigDecimal(0.00);
        var skills = new String[]{};

        //when
        var pageable = getPagingRequest(0,10, new String[]{"posted_date|desc"});
        Mockito.when(jobRepository.search(keyWords,
                                            String.join("|", employmentTypes),
                                            String.join("|", workLocations),
                                            minimumSalary,
                                            String.join("|",skills), pageable))
                                    .thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.searchJob(0, 10, new String[]{"posted_date|desc"} , keyWords, employmentTypes
                                                    , workLocations, minimumSalary, skills);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());
    }

    @Test
    void givenBlankKeyword_ReturnJobList(){
        //given
        var jobs = testJobListing;
        var keyWords = "";
        var employmentTypes = new String[]{};
        var workLocations = new String[]{};
        var minimumSalary = new BigDecimal(0.00);
        var skills = new String[]{};
        //when
        var pageable = getPagingRequest(0,10, new String[]{"posted_date|desc"});
        Mockito.when(jobRepository.search(keyWords,
                        String.join("|", employmentTypes),
                        String.join("|", workLocations),
                        minimumSalary,
                        String.join("|",skills), pageable))
                .thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.searchJob(0, 10, new String[]{"posted_date|desc"} , keyWords, employmentTypes
                , workLocations, minimumSalary, skills);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());
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

    @Test
    void givenIdAndUserId_ReturnJob() {
        // Given
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        Mockito.when(jobRepository.findById(1L)).thenReturn(Optional.of(new Job()));
        Mockito.when(jobApplicationRepository.findByUserIdAndJobId(uuidString, 1L)).thenReturn(Optional.of(List.of(new JobApplication())));
        // When
        JobDto jobDto = jobServiceImpl.retrieveJob(1L ,uuidString);

        // Then
        Assertions.assertNotNull(jobDto);
    }

    private Pageable getPagingRequest(int index, int pageSize, String[] sort){
        ArrayList<Sort.Order> sortOrder = new ArrayList<>();
        for (String sortBy : sort) {
            var sortParams = sortBy.split("\\|");
            var sortDirection = Sort.Direction.ASC;
            if(sortParams.length >= 2){
                if(sortParams[1].equalsIgnoreCase(Sort.Direction.DESC.toString()))
                    sortDirection = Sort.Direction.DESC;
            }
            if(sortParams.length >=1) sortBy = sortParams[0];
            sortOrder.add(new Sort.Order(sortDirection,sortBy));
        }
        return PageRequest.of(index,pageSize, Sort.by(sortOrder) );
    }

}
