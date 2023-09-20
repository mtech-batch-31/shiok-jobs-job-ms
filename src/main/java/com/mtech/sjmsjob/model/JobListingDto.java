package com.mtech.sjmsjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListingDto {
    private Integer index;

    private Integer pageSize;

    private Long totalRecord;

    private String sortBy;

    private Iterable<JobSummaryDto> data;
}
