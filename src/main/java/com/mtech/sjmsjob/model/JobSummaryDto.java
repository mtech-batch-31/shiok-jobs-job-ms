package com.mtech.sjmsjob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSummaryDto {

    private long id;
    private long companyId;

    private String companyName;
    private String jobTitle;

    private String jobSummary;

    private Date postedDate;

    private Date closingDate;

}
