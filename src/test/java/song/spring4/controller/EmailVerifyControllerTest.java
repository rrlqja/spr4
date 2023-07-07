package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import song.spring4.service.EmailVerificationTokenService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmailVerifyControllerTest {

    @Autowired
    EmailVerificationTokenService emailVerificationTokenService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void verify1() throws Exception {
        mockMvc.perform(post("/verifyEmail").param("email", "dkclasltmf@naver.com"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/verifyEmail").param("email", "dkclasltmf22@naver.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verify2() throws Exception {
        String token = emailVerificationTokenService.createToken("dkclasltmf@naver.com");

        mockMvc.perform(post("/verifyEmail/{token}", token))
                .andExpect(status().isOk());

        mockMvc.perform(post("/verifyEmail/any"))
                .andExpect(status().isBadRequest());
    }

}