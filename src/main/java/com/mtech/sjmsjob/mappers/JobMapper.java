package com.mtech.sjmsjob.mappers;


import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobDto;
import com.mtech.sjmsjob.model.JobSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    default JobSummaryDto jobToJobSummaryDto(Job job){
        return JobSummaryDto
                .builder()
                .id(job.getId())
                .company(job.getCompanyName())
                .logo("https://bucketurl/"+job.getCompanyName())
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

    JobDto jobToJobDto(Job job);
}
