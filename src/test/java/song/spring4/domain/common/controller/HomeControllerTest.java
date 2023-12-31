package song.spring4.domain.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import song.spring4.domain.user.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    void getSignup() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void postSignup() throws Exception {
        String username = "testUsername";

        mockMvc.perform(post("/signup").param("username", username)
                        .param("password", "testPassword").param("name", "testName")
                        .param("email", "test@email.com"))
                .andExpect(status().isCreated());

//        User findUser = userService.findUserByUsername(username);

//        assertThat(findUser.getEmail()).isEqualTo("test@email.com");
    }
}