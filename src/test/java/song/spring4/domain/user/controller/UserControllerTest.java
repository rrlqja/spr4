package song.spring4.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import song.spring4.domain.user.dto.ResponseUserDto;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @DisplayName("회원 조회")
    @Test
    @WithUserDetails("a")
    void getUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/user"))
                .andReturn();

        Object userObj = result.getModelAndView().getModel().get("user");
        assertThat(userObj)
                .isInstanceOf(ResponseUserDto.class);

        ResponseUserDto user = (ResponseUserDto) userObj;
        assertThat(user.getUsername())
                .isEqualTo("a");
    }

    @DisplayName("username 변경 요청")
    @Test
    @WithUserDetails("a")
    void updateUsername() throws Exception {
        mockMvc.perform(post("/user/updateUsername")
                        .param("username", "newTestUsername"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/updateUsername")
                        .param("username", "a"))
                .andExpect(status().isOk());
    }

    @DisplayName("비밀번호 변경 예외 요청, 정상 변경 요청")
    @Test
    @WithUserDetails("a")
    void updatePassword() throws Exception {
        mockMvc.perform(post("/user/updatePassword").param("originalPassword", "a")
                        .param("newPassword", "a"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/user/updatePassword").param("originalPassword", "a")
                        .param("newPassword", "new password"))
                .andExpect(status().isOk());
    }
}