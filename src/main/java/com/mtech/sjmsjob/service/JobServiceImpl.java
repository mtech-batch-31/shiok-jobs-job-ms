package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.mappers.JobMapper;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    final private JobRepository jobRepository;

    private final JobMapper jobMapper = JobMapper.INSTANCE;

    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public JobListingDto listJobs(int index, int pageSize, String[] sort) {
        Page<Job> jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
        return JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
    }

//    public JobListingDto searchJobs(int index, int pageSize, String[] sort, String[] jobTitles){
//        Page<Job> jobs;
//        JobListingDto result;
//        if(jobTitles.length > 0){
//            jobs = this.jobRepository.search(jobTitles, getPagingRequest(index,pageSize, sort ));
//            return null;
//       }
//       else {
//            jobs = this.jobRepository.findAll(getPagingRequest(index,pageSize, sort ));
//       }
//        result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
//        return result;
//    }

//    public JobListingDto searchJobs(int index, int pageSize, String[] sort, String keyWords, String[] employmentType) {
//        Page<Job> jobs;
//        JobListingDto result = null;
//        if (keyWords.length() > 0) {
//            //form search terms (simple version)
//            keyWords = keyWords.trim().replaceAll("\\s+", "|");
//            jobs = this.jobRepository.searchOld(keyWords, getPagingRequest(index, pageSize, sort));
//        }
//        else {
//            jobs = this.jobRepository.findAll(getPagingRequest(index, pageSize, sort));
//        }
//        if (jobs != null)
//            result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);
//
//        return result;
//    }

    public JobListingDto searchJob(int index, int pageSize, String[] sort, String keyWords, String[] employmentType
            , String[] workLocations, BigDecimal minimumSalary, String[] skills) {
        Page<Job> jobs;
        JobListingDto result = null;
        String employmentTypesConcat = String.join("|",employmentType);
        String workLocationsConcat = String.join("|",workLocations);
        String skillsConcat = String.join("|",skills);

        //replace spaces with | delimiter for fulltext search
        keyWords = keyWords.trim().replaceAll("\\s+", "|");
        jobs = this.jobRepository.search(keyWords, employmentTypesConcat, workLocationsConcat, minimumSalary, skillsConcat, getPagingRequest(index, pageSize, sort));

        if (jobs != null)
            result = JobMapper.INSTANCE.pageJobToJobListingDto(jobs);

        return result;
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

    public JobDto retrieveJob(long id) {
        Optional<Job> job = this.jobRepository.findById(id);
        if(job.isEmpty()) {
            return null;
        }
        return jobMapper.jobToJobDto(job.get());
    }

}
