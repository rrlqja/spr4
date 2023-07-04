package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import song.spring4.dto.FindPasswordDto;
import song.spring4.entity.User;
import song.spring4.exception.TokenNotFoundException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.service.UserService;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Test
    void findPassword1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user/findPassword"))
                .andExpect(view().name("user/findPassword"))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void findPassword2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf@naver.com")
                .param("username", "u1").param("name", "n1")).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("이메일로 전송된 링크로 접속해주세요.");
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void resetPassword1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf@naver.com")
                .param("username", "u1").param("name", "n1")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String url = result.replace("localhost:8080", "");

        MvcResult restPasswordResult = mockMvc.perform(get(url))
                .andExpect(view().name("user/resetPassword"))
                .andReturn();

        assertThat(restPasswordResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void resetPassword2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf@naver.com")
                .param("username", "u1").param("name", "n1")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String url = result.replace("localhost:8080", "");

        MvcResult resetPasswordResult = mockMvc.perform(post(url).param("newPassword", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(content().json("1"))
                .andReturn();

        String resultString = resetPasswordResult.getResponse().getContentAsString();

        User findUser = userService.findUserById(Long.valueOf(resultString));
        assertThat(passwordEncoder.matches("newPassword", findUser.getPassword())).isTrue();

    }

}