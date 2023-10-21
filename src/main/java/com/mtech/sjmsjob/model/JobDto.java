package com.mtech.sjmsjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    private long id;
    private long companyId;

    private String companyName;

    private String jobTitle;

    private String jobSummary;

    private String jobCategory;

    private String level;

    private List<String> skills;

    private String employmentType;

    private String location;

    private String workHours;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    private Date postedDate;

    private Date closingDate;

    private Integer version;

    private String lastUpdatedBy;
    private Date lastUpdatedTime;

    private String createdBy;

    private Date createdTime;

    private Boolean applied;

}
