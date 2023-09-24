package com.mtech.sjmsjob.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSummaryDto {

    private long id;

    private String company;

    private String logo;

    private String jobTitle;

    private String salaryRange;

    private String level;

    private String postedAt;

    private String employmentType;

    private String location;

    private List<String> skills;

    private Date closingDate;

}
