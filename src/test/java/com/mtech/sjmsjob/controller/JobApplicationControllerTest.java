package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.service.JobApplicationService;
import com.mtech.sjmsjob.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
public class JobApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService jobApplicationService;

    @MockBean
    private JwtTokenUtil tokenUtil;

    private  void initAuth(){
        given(tokenUtil.getUserNameFromJWT(any(String.class))).willReturn("testuser");
    }

//    @Test
//    void givenApplyJobWhenValidJobId() throws Exception{
//
//        mockMvc.perform(post("/v1/jobappl/apply")
//                            .content("1")
//                            .contentType(MediaType.TEXT_PLAIN)
//                            .header("Authorization","Bearer 1234"))
//                .andExpect(status().isOk());
//    }
}
