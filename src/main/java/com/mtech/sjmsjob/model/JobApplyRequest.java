package com.mtech.sjmsjob.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class JobApplyRequest {

    @NotNull(message= "id cannot be null")
    @Min(value = 1, message = "invalid id")
    private Long id;

}

