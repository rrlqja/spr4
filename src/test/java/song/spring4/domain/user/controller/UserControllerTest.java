package song.spring4.domain.user.controller;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.service.UserService;
import song.spring4.security.service.UserDetailsServiceImpl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
//        UserDetails userDetails = userDetailsService.loadUserByUsername("a");
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
    @Transactional
    @Rollback(value = true)
    void postUpdateUsername() throws Exception {
        mockMvc.perform(post("/user/updateUsername")
                        .param("username", "username").with(securityContext(context)))
                .andExpect(status().isOk());

//        assertThat(userService.findUserByUsername("username")).isNotNull();

//        mockMvc.perform(post("/user/updateUsername")
//                        .param("username", "username").with(securityContext(context)))
//                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void postUpdatePassword() throws Exception {
        mockMvc.perform(post("/user/updatePassword").param("originalPassword", "a")
                        .param("newPassword", "new password").with(securityContext(context)))
                .andExpect(status().isOk());
//        User findUser = userService.findUserByUsername("a");
//        assertThat(passwordEncoder.matches("new password", findUser.getPassword())).isEqualTo(true);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void postUpdateName() throws Exception {
        mockMvc.perform(post("/user/updateName")
                        .param("name", "name").with(securityContext(context)))
                .andExpect(status().is3xxRedirection());

//        assertThat(userService.findUserByUsername("a").getName()).isEqualTo("name");
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void postUpdateEmail() throws Exception {
        mockMvc.perform(post("/user/updateEmail")
                        .param("email", "newEmail@email.com"))
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
}