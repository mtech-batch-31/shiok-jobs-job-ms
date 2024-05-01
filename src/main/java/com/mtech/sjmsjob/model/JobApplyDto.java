package com.mtech.sjmsjob.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobApplyDto {

    private String status;

    private String message;
}
