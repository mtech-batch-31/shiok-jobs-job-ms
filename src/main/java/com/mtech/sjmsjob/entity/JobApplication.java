package com.mtech.sjmsjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    private UUID userId;

    private Date appliedDate;

    private String status;

    private Boolean seekerStatus;

    @Version
    private Integer version;

    private String lastUpdatedBy;

    @UpdateTimestamp
    private Date lastUpdatedTime;

    private String createdBy;

    @CreationTimestamp
    private Date createdTime;

    private Date seekerStatusLastUpdatedDate ;
}

