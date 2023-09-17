package com.mtech.sjmsjob.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long companyId;

    private String companyName;

    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    private String jobSummary;

    private String jobCategory;

    private String level;

    private String skills;

    private String employmentType;

    private String location;

    private String workHours;

    private Float minSalary;
    private Float maxSalary;

    private Date postedDate;

    private Date closingDate;

    @Version
    private Integer version;

    private Date lastUpdatedTime;

    private String lastUpdatedBy;

    private Date createdTime;

    private String createdBy;
}
