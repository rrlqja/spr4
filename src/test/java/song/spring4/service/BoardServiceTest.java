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
import song.spring4.dto.boarddto.BoardDto;
import song.spring4.domain.comment.dto.BoardListDto;
import song.spring4.dto.boarddto.SaveBoardDto;
import song.spring4.dto.commentdto.CommentDto;
import song.spring4.dto.boarddto.EditBoardDto;
import song.spring4.domain.board.Board;
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
    @Autowired
    EntityManager em;

    @Test
    void save1() {
        SaveBoardDto saveBoardDto = new SaveBoardDto();
        saveBoardDto.setTitle("title");
        saveBoardDto.setContent("content");

        Long id = boardService.saveBoard(1L, saveBoardDto);

        assertThat(boardRepository.findEntityGraphById(id).get().getTitle())
                .isEqualTo(saveBoardDto.getTitle());
    }

    @Test
    void find1() {
        SaveBoardDto saveBoardDto = new SaveBoardDto();
        saveBoardDto.setTitle("title");
        saveBoardDto.setContent("content");
        Long id = boardService.saveBoard(1L, saveBoardDto);

        Board findBoard = boardRepository.findEntityGraphById(id).get();

        assertThat(findBoard.getContent()).isEqualTo("content");
    }

    @Test
    void find2() {
        BoardDto findBoardDto = boardService.findBoardById(1L);
        assertThat(findBoardDto.getCommentList().size()).isEqualTo(5);
        for (CommentDto commentDto : findBoardDto.getCommentList()) {
            log.info("comment writer = {}", commentDto.getWriterUsername());
        }
    }

    @Test
    void find3() {
        BoardDto findBoardDto = boardService.findBoardById(1L);
        log.info("title = {}", findBoardDto.getTitle());
        log.info("content = {}", findBoardDto.getContent());
        log.info("writer = {}", findBoardDto.getWriterUsername());

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
        SaveBoardDto saveBoardDto = new SaveBoardDto();
        saveBoardDto.setTitle("title");
        saveBoardDto.setContent("content");
        Long id = boardService.saveBoard(1L, saveBoardDto);

        EditBoardDto editBoardDto = new EditBoardDto();
        editBoardDto.setTitle("update title");
        editBoardDto.setContent("update content");
        Long updateId = boardService.editBoard(id, editBoardDto);

        assertThat(boardRepository.findEntityGraphById(updateId).get().getTitle())
                .isEqualTo(editBoardDto.getTitle());
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