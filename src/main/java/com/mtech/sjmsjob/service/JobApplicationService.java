package com.mtech.sjmsjob.service;

import com.mtech.sjmsjob.entity.JobApplication;

public interface JobApplicationService {
    JobApplication applyJob(String userId, long jobId);
}
