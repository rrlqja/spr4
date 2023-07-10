package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.User;
import song.spring4.service.ResetPasswordService;
import song.spring4.service.UserService;
import song.spring4.userdetails.UserDetailsServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    ResetPasswordService resetPasswordService;
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    MockMvc mockMvc;

    SecurityContext context;

    @BeforeEach
    void init() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("a");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
    }

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/user").with(securityContext(context)))
                .andExpect(status().isOk())
                .andExpect(view().name("user/user"));
    }

    @Test
    void postUpdateUsername() throws Exception {
        mockMvc.perform(post("/user/updateUsername")
                        .param("newUsername", "newUsername").with(securityContext(context)))
                .andExpect(status().isOk());

        assertThat(userService.findUserByUsername("newUsername")).isNotNull();

        mockMvc.perform(post("/user/updateUsername")
                        .param("newUsername", "newUsername").with(securityContext(context)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postUpdatePassword() throws Exception {
        mockMvc.perform(post("/user/updatePassword").param("origPassword", "a")
                        .param("newPassword", "newPassword").with(securityContext(context)))
                .andExpect(status().isOk());
        User findUser = userService.findUserByUsername("a");
        assertThat(passwordEncoder.matches("newPassword", findUser.getPassword())).isEqualTo(true);
    }

    @Test
    void postUpdateName() throws Exception {
        mockMvc.perform(post("/user/updateName")
                        .param("newName", "newName").with(securityContext(context)))
                .andExpect(status().is3xxRedirection());

        assertThat(userService.findUserByUsername("a").getName()).isEqualTo("newName");
    }

    @Test
    void postUpdateEmail() throws Exception {
        mockMvc.perform(post("/user/updateEmail")
                        .param("newEmail", "newEmail@email.com"))
                .andExpect(status().isOk());
    }

    @Test
    void postFindUsername() throws Exception {
        mockMvc.perform(post("/user/findUsername")
                        .param("name", "a").param("email", "dkclasltmf22@naver.com"))
                .andExpect(jsonPath("$.username").value("a"))
                .andExpect(status().isOk());
    }


    @Test
    void postFindPassword() throws Exception {
        mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf22@naver.com")
                        .param("username", "a").param("name", "a"))
                .andExpect(status().isCreated());
    }


    @Test
    void postResetPassword() throws Exception {
        String token = resetPasswordService.createPasswordToken("dkclasltmf22@naver.com");

        mockMvc.perform(post("/user/resetPassword/{token}", token)
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk());
    }

}