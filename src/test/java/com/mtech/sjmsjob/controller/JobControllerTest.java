package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobServiceImpl jobService;
    @Test
    void givenEmptySearchCriteria_returnSuccessfulResponse() throws Exception {
        mockMvc.perform(get("/api/v1/jobs"))
                .andExpect(status().isOk());
   }

    @Test
    void givenJobId_returnSuccessfulResponse() throws Exception {
        mockMvc.perform(get("/api/v1/jobs/1"))
                .andExpect(status().isOk());
    }

}
