package song.spring4.domain.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.board.entity.Board;
import song.spring4.exception.notfound.exceptions.BoardNotFoundException;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.board.service.BoardService;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class BoardControllerTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardJpaRepository boardRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails("a")
    void saveBoard() throws Exception {
        mockMvc.perform(post("/board/save").param())
    }

//    @Test
//    void getBoard1() throws Exception {
//        mockMvc.perform(get("/board/{id}", 1L))
//                .andExpect(model().attribute("boardDto", hasProperty("title", is("title1"))))
//                .andExpect(model().attribute("boardDto", hasProperty("commentList", hasSize(5))));
//
//    }
//
//    @Test
//    void postSaveBoard1() throws Exception {
//        int beforeSize = boardRepository.findAll().size();
//        mockMvc.perform(post("/board/save").param("title", "testTitle")
//                        .param("content", "testContent").with(securityContext(context)))
//                .andExpect(redirectedUrlPattern("/board/*"));
//
//        int afterSize = boardRepository.findAll().size();
//
//        assertThat(beforeSize).isEqualTo(afterSize - 1);
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = true)
//    void postEditBoard1() throws Exception {
//        mockMvc.perform(post("/board/{id}/edit", 1L).with(securityContext(context))
//                        .param("title", "testEditTitle").param("content", "testEditContent"))
//                .andExpect(redirectedUrlPattern("/board/*"));
//
//        Board findBoard = boardRepository.findById(1L).get();
//        assertThat(findBoard.getTitle()).isEqualTo("testEditTitle");
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = true)
//    void postDeleteBoard1() throws Exception {
//        mockMvc.perform(post("/board/{id}/delete", 1L).with(securityContext(context)))
//                .andExpect(redirectedUrl("/"));
//
//        assertThatThrownBy(() -> boardService.findBoardById(1L)).isInstanceOf(BoardNotFoundException.class);
//    }
}