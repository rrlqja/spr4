package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import song.spring4.service.ResetPasswordService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    ResetPasswordService resetPasswordService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void findPassword1() throws Exception {
        mockMvc.perform(get("/user/findPassword"))
                .andExpect(view().name("user/findPassword"))
                .andExpect(status().isOk());
    }

    @Test
    void findPassword2() throws Exception {
        mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf@naver.com")
                        .param("username", "u1").param("name", "n1"))
                .andExpect(status().isCreated());
    }

    @Test
    void resetPassword1() throws Exception {
        mockMvc.perform(get("/user/resetPassword/123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void resetPassword2() throws Exception {
        String token = resetPasswordService.createPasswordToken("dkclasltmf@naver.com");

        mockMvc.perform(post("/user/resetPassword/{token}", token)
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk());
    }

}