package song.spring4.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import song.spring4.entity.Board;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.service.BoardService;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BoardControllerTest {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardJpaRepository boardRepository;
    @Autowired
    UserDetailsService userDetailsService;
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
    void getBoard1() throws Exception {
        mockMvc.perform(get("/board/{id}", 1L))
                .andExpect(model().attribute("boardDto", hasProperty("title", is("title0"))))
                .andExpect(model().attribute("boardDto", hasProperty("commentList", hasSize(2))));

    }

    @Test
    void postSaveBoard1() throws Exception {
        int beforeSize = boardRepository.findAll().size();
        mockMvc.perform(post("/board/save").param("title", "testTitle")
                        .param("content", "testContent").with(securityContext(context)))
                .andExpect(redirectedUrlPattern("/board/*"));

        int afterSize = boardRepository.findAll().size();

        assertThat(beforeSize).isEqualTo(afterSize - 1);
    }

    @Test
    void postEditBoard1() throws Exception {
        mockMvc.perform(post("/board/{id}/edit", 1L).param("title", "testEditTitle")
                        .param("content", "testEditContent"))
                .andExpect(redirectedUrlPattern("/board/*"));

        Board findBoard = boardRepository.findById(1L).get();
        assertThat(findBoard.getTitle()).isEqualTo("testEditTitle");
    }

    @Test
    void postDeleteBoard1() throws Exception {
        mockMvc.perform(post("/board/{id}/delete", 1L).with(securityContext(context)))
                .andExpect(redirectedUrl("/"));

        assertThatThrownBy(() -> boardService.findBoardById(1L)).isInstanceOf(BoardNotFoundException.class);
    }


}