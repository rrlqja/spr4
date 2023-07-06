package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import song.spring4.service.EmailVerificationTokenService;

import static org.junit.jupiter.api.Assertions.*;

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
    void send1() {
        mockMvc.perform()
    }

}