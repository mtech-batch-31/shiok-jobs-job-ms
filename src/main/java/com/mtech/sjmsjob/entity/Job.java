package com.mtech.sjmsjob.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "job_skills", joinColumns = @JoinColumn(name = "id"))
    private List<String> skills;

    private String employmentType;

    private String location;

    private String workHours;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    private Date postedDate;

    private Date closingDate;

    @Version
    private Integer version;

    private String lastUpdatedBy;

    @UpdateTimestamp
    private Date lastUpdatedTime;

    private String createdBy;

    @CreationTimestamp
    private Date createdTime;


}
