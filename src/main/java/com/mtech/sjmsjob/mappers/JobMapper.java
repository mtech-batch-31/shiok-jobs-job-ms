package com.mtech.sjmsjob.mappers;


import com.mtech.sjmsjob.entity.Job;
import com.mtech.sjmsjob.model.JobSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);
    JobSummaryDto jobToJobSummaryDto(Job job);
}
