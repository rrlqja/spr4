package song.spring4.domain.board.controller;

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
import song.spring4.domain.board.dto.ResponseBoardDto;

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
class BoardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails("a")
    void saveBoard() throws Exception {
        String title = "mock title";
        MvcResult result = mockMvc.perform(post("/board/save")
                        .param("title", title)
                        .param("content", "mock content"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        String boardId = redirectedUrl.substring(redirectedUrl.lastIndexOf("/") + 1);

        MvcResult boardResult = mockMvc.perform(get("/board/{boardId}", Long.valueOf(boardId)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(boardResult.getModelAndView().getModel().get("board"))
                .isInstanceOf(ResponseBoardDto.class);
        ResponseBoardDto board = (ResponseBoardDto) boardResult.getModelAndView().getModel().get("board");

        assertThat(board.getTitle())
                .isEqualTo(title);
    }

    @Test
    @WithUserDetails("a")
    void deleteBoard() throws Exception{
        mockMvc.perform(post("/board/{boardId}/delete", 1L))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("작성자가 아닌 사용자가 게시글 삭제시 예외 발생")
    @Test
    @WithUserDetails("b")
    void deleteBoardException() throws Exception{
        mockMvc.perform(post("/board/{boardId}/delete", 1L))
                .andExpect(status().isBadRequest());
    }
}