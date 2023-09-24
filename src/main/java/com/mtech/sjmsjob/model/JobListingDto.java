package com.mtech.sjmsjob.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListingDto {
    private Integer index;

    private Integer pageSize;

    private Long totalRecord;

    private String sortBy;

    private Iterable<JobSummaryDto> data;
}
