package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.dto.RequestBoardDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.dto.UpdateBoardDto;
import song.spring4.entity.Board;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.repository.BoardJpaRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardJpaRepository boardRepository;

    @Test
    void save1() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");

        Long id = boardService.saveBoard(1L, requestBoardDto);

        assertThat(boardRepository.findEntityGraphById(id).get().getTitle())
                .isEqualTo(requestBoardDto.getTitle());
    }

    @Test
    void find1() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");
        Long id = boardService.saveBoard(1L, requestBoardDto);

        Board findBoard = boardRepository.findEntityGraphById(id).get();

        assertThat(findBoard.getContent()).isEqualTo("content");
    }

    @Test
    void find2() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ResponseBoardDto> boardPage = boardService.findBoardList(pageRequest);
        assertThat(boardPage.getNumber()).isEqualTo(0);
    }

    @Test
    void update1() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");
        Long id = boardService.saveBoard(1L, requestBoardDto);

        UpdateBoardDto updateBoardDto = new UpdateBoardDto();
        updateBoardDto.setTitle("update title");
        updateBoardDto.setContent("update content");
        Long updateId = boardService.updateBoard(id, updateBoardDto);

        assertThat(boardRepository.findEntityGraphById(updateId).get().getTitle())
                .isEqualTo(updateBoardDto.getTitle());
    }

    @Test
    void delete1() {
        boardService.deleteBoard(1L, 1L);

        assertThatThrownBy(() -> boardService.findBoardById(1L))
                .isInstanceOf(BoardNotFoundException.class);
    }
}