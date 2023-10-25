/*
* API for listing and creating job applications
 */
package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
public class JobApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService jobApplicationService;

    @Test
    void givenApplyJobWhenInValidJobId_return404Response() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .content("abc"))
                .andExpect(status().isBadRequest());

    }
    @Test
    void givenApplyJobWhenInValidUserId_return404Response() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply").content("1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/jobs/apply").content("1")
                        .header("user-id", "somethinginvalid")
                        .content("abc"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void givenApplyJobWhenValidJobId_returnSuccessfulResponse() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .content("1"))
                .andExpect(status().isOk());
    }
}
