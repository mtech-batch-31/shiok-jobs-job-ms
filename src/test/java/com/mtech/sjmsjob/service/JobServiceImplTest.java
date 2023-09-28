package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {


    @InjectMocks
    private JobServiceImpl jobServiceImpl;

    @Mock
    private JobRepository jobRepository;

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
                    , postedDate1, closingDate,1, "test", new Date(), "test", new Date() ));

            add(new Job(2,1,"companyB","Technical manager", "manage project"
                    , "information technology", "manager"
                    , new ArrayList<>(){{add("project management"); add("prince");}}, "full-time"
                    , "hybrid", "9am - 6pm"
                    , new BigDecimal(20000.00), new BigDecimal(25000.00)
                    , postedDate2, closingDate,1, "test", new Date(), "test", new Date() ));

            add(new Job(3,1,"companyC","Lead Consultant", "lead engineer"
                    , "information technology", "senior"
                    , new ArrayList<>(){{add("java");}}, "part-time", "hybrid"
                    , "9am - 6pm", new BigDecimal(20000.00), new BigDecimal(25000.00)
                    , postedDate3, closingDate,1, "test", new Date(), "test", new Date() ));
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
        var pageable = getPagingRequest(0,10, new String[]{"posted_date,desc"});
        Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.listJobs(0, 10, new String[]{"posted_date,desc"} );

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());
        var data = result.getData();
        JobSummaryDto record1 = data.iterator().next();
        JobSummaryDto record2 = data.iterator().next();
        JobSummaryDto record3 = data.iterator().next();
        Assertions.assertTrue(record1.getPostedAt().compareTo(record2.getPostedAt()) >= 0);
        Assertions.assertTrue(record2.getPostedAt().compareTo(record3.getPostedAt()) >= 0);
    }

    @Test
    void givenSearchByKeyword_ReturnJobList(){
        //given
        var jobs = testJobListing;
        var keyWords = "engineer|consultant";
        //when
        var pageable = getPagingRequest(0,10, new String[]{"posted_date,desc"});
        Mockito.when(jobRepository.search(keyWords, pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        //Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.searchJobs(0, 10, new String[]{"posted_date,desc"} , keyWords);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalRecord());

    }

    @Test
    void givenBlankKeyword_ReturnJobList(){
        //given
        var jobs = testJobListing;
        var keyWords = "";
        //when
        var pageable = getPagingRequest(0,10, new String[]{"posted_date,desc"});
        //Mockito.when(jobRepository.search(keyWords, pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        Mockito.when(jobRepository.findAll(pageable)).thenReturn(new PageImpl<>(jobs, pageable, 3));
        JobListingDto result = jobServiceImpl.searchJobs(0, 10, new String[]{"posted_date,desc"} , keyWords);

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

    private Pageable getPagingRequest(int index, int pageSize, String[] sort){
        ArrayList<Sort.Order> sortOrder = new ArrayList<>();
        for (String sortBy : sort) {
            sortOrder.add(new Sort.Order(Sort.Direction.ASC,sortBy));
        }
        return PageRequest.of(index,pageSize, Sort.by(sortOrder) );
    }

}
