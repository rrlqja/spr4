package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import song.spring4.service.ResetPasswordService;
import song.spring4.userdetails.UserDetailsServiceImpl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getUser() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("a");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);

        mockMvc.perform(get("/user").with(securityContext(securityContext)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("a"));
    }

    @Test
    void getFindUsername() throws Exception {
        mockMvc.perform(get("/user/findUsername"))
                .andExpect(view().name("user/findUsername"))
                .andExpect(status().isOk());
    }

    @Test
    void postFindUsername() throws Exception {
        mockMvc.perform(post("/user/findUsername")
                        .param("name", "a").param("email", "dkclasltmf@naver.com"))
                .andExpect(jsonPath("$.username").value("a"))
                .andExpect(status().isOk());
    }

    @Test
    void getFindPassword() throws Exception {
        mockMvc.perform(get("/user/findPassword"))
                .andExpect(view().name("user/findPassword"))
                .andExpect(status().isOk());
    }

    @Test
    void postFindPassword() throws Exception {
        mockMvc.perform(post("/user/findPassword").param("email", "dkclasltmf@naver.com")
                        .param("username", "a").param("name", "a"))
                .andExpect(status().isCreated());
    }

    @Test
    void getResetPassword() throws Exception {
        mockMvc.perform(get("/user/resetPassword/123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postResetPassword() throws Exception {
        String token = resetPasswordService.createPasswordToken("dkclasltmf@naver.com");

        mockMvc.perform(post("/user/resetPassword/{token}", token)
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk());
    }

}