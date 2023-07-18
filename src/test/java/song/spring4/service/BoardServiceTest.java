package song.spring4.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.*;
import song.spring4.entity.Board;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.LogEntityJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class BoardServiceTest {

    @Autowired
    LogEntityJpaRepository logEntityRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    BoardJpaRepository boardRepository;
    @Autowired
    EntityManager em;

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
        ResponseBoardDto findBoardDto = boardService.findBoardById(1L);
        assertThat(findBoardDto.getCommentList().size()).isEqualTo(5);
        for (CommentDto commentDto : findBoardDto.getCommentList()) {
            log.info("comment writer = {}", commentDto.getWriterUsername());
        }
    }

    @Test
    void find3() {
        ResponseBoardDto findBoardDto = boardService.findBoardById(1L);
        log.info("title = {}", findBoardDto.getTitle());
        log.info("content = {}", findBoardDto.getContent());
        log.info("writer = {}", findBoardDto.getWriter());

        for (CommentDto commentDto : findBoardDto.getCommentList()) {
            log.info("comment = {}", commentDto.getContent());
            log.info("comment writer = {}", commentDto.getWriterUsername());
            log.info("child size = {}", commentDto.getChildList().size());
        }
    }

    @Test
    void find4() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListDto> boardPage = boardService.findBoardList(pageRequest);
        assertThat(boardPage.getNumber()).isEqualTo(0);
    }

    @Test
    void find5() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardListDto> boardPage = boardService.findBoardListByTitle("le", pageRequest);
        assertThat(boardPage.getTotalElements()).isEqualTo(31);
    }

    @Test
    void find6() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardListDto> boardPage = boardService.findBoardListByUsername("a", pageRequest);
        assertThat(boardPage.getTotalElements()).isEqualTo(31);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void update1() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");
        Long id = boardService.saveBoard(1L, requestBoardDto);

        EditBoardDto editBoardDto = new EditBoardDto();
        editBoardDto.setTitle("update title");
        editBoardDto.setContent("update content");
        Long updateId = boardService.editBoard(id, editBoardDto);

        assertThat(boardRepository.findEntityGraphById(updateId).get().getTitle())
                .isEqualTo(editBoardDto.getTitle());
    }

    @Test
    void update2() {
        Integer result = boardService.bulkUpdateBoardTitle("title","update");

        List<Board> boardList = boardRepository.findAll();

        assertThat(result).isEqualTo(boardList.size());

        for (Board board : boardList) {
            System.out.println("board.getTitle() = " + board.getTitle());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void delete1() {
        boardService.deleteBoard( 1L);

        assertThatThrownBy(() -> boardService.findBoardById(1L))
                .isInstanceOf(BoardNotFoundException.class);
    }
}