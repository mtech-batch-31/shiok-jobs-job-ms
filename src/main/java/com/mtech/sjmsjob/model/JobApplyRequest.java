package com.mtech.sjmsjob.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobApplyRequest {
    private long id;

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }
}

