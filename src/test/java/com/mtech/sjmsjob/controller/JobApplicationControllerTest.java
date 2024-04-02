/*
* API for listing and creating job applications
 */
package com.mtech.sjmsjob.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtech.sjmsjob.model.JobApplyRequest;
import com.mtech.sjmsjob.service.JobApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
@WebMvcTest(JobApplicationController.class)
class JobApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService jobApplicationService;

    private String ValidJsonString;

    private String InvalidJsonString;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        JobApplyRequest jobApplyRequest = new JobApplyRequest();
        jobApplyRequest.setId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        ValidJsonString = objectMapper.writeValueAsString(jobApplyRequest);

        JobApplyRequest invalidJsonApplyRequest = new JobApplyRequest();
        invalidJsonApplyRequest.setId(0L);
        InvalidJsonString = objectMapper.writeValueAsString(invalidJsonApplyRequest);
    }

    @Test
    void givenApplyJobWhenInValidJobId_return404Response() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(InvalidJsonString))
                .andExpect(status().isBadRequest());


    }
    @Test
    void givenApplyJobWhenInValidUserId_return404Response() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply").content("1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/jobs/apply").content("1")
                        .header("user-id", "somethinginvalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ValidJsonString))
                .andExpect(status().isBadRequest());

    }

    @Test
    void givenApplyJobWhenValidJobId_returnSuccessfulResponse() throws Exception {

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ValidJsonString))
                .andExpect(status().isOk());
    }

    @Test
    void givenApplyJobWhenEmptyOrInvalidJobId_returnBadRequestResponse() throws Exception {


        MvcResult result = mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals("{\"returnCode\":\"400\",\"message\":\"id cannot be null\"}", actual);
    }

    @Test
    void givenApplyJobWhenEmptyOrInvalidUserId_returnBadRequestResponse() throws Exception {

        MvcResult result = mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", " ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ValidJsonString))
                .andExpect(status().isBadRequest()).andReturn();

        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals("Missing or Invalid User Id", actual);
    }

    @Test
    void givenApplyJobWhenThrowExceptionsFromService_returnInternalServerErrorResponse() throws Exception {
        UUID anyUserId = UUID.fromString("3af5923e-aeee-4c79-bb2d-4cbea3e03bd3");
        long anyJobId = 1L;

        given(jobApplicationService.applyJob(anyUserId, anyJobId)).willThrow(new IllegalArgumentException("job exists"));

        mockMvc.perform(post("/v1/jobs/apply")
                        .header("user-id", "3af5923e-aeee-4c79-bb2d-4cbea3e03bd3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ValidJsonString))
                .andExpect(status().isInternalServerError());
    }

}
