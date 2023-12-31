package com.mtech.sjmsjob.mappers;


import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobListingDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.ArrayList;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    default JobSummaryDto jobToJobSummaryDto(Job job){
        return JobSummaryDto
                .builder()
                .id(job.getId())
                .company(job.getCompanyName())
                .jobTitle(job.getJobTitle())
                .salaryRange(job.getMinSalary()+" - "+job.getMaxSalary())
                .level(job.getLevel())
                .postedAt(job.getPostedDate().toString())
                .employmentType(job.getEmploymentType())
                .location(job.getLocation())
                .skills(job.getSkills())
                .closingDate(job.getClosingDate())
                .build();

    }

    default JobListingDto pageJobToJobListingDto(Page<Job> jobs){
        ArrayList<JobSummaryDto> joblist = new ArrayList<>();

        for (Job job: jobs) {
            joblist.add(JobMapper.INSTANCE.jobToJobSummaryDto(job));
        }

        return JobListingDto
                .builder()
                .index(jobs.getNumber())
                .pageSize(jobs.getSize())
                .totalRecord(jobs.getTotalElements())
                .data(joblist)
                .build();
    }

    @Mapping(target = "applied", ignore = true)
    JobDto jobToJobDto(Job job);
}
