package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.JobApplication;

import java.util.UUID;

public interface JobApplicationService {
    JobApplication applyJob(UUID userId, long jobId);
}
