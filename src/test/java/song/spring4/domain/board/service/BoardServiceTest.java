package song.spring4.domain.board.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.board.dto.BoardListDto;
import song.spring4.domain.board.dto.EditBoardDto;
import song.spring4.domain.board.dto.RequestBoardDto;
import song.spring4.domain.board.dto.ResponseBoardDto;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.file.repository.FileEntityJpaRepository;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.user.repository.UserJpaRepository;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.exception.notfound.NotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardJpaRepository boardRepository;
    @Autowired
    FileEntityJpaRepository fileEntityRepository;
    @Autowired
    UserJpaRepository userRepository;

    User userA;

    @BeforeEach
    void beforeEach() {
        fileEntityRepository.deleteAll();
        boardRepository.deleteAll();
        userA = userRepository.findById(1L).get();
        log.info("=======================================================================");
        log.info("============================= before each =============================");
        log.info("=======================================================================");
    }

    @DisplayName("게시글 저장")
    @Test
    void save() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("test title");
        requestBoardDto.setContent("test content");

        Long saveBoardId = boardService.saveBoard(userA.getId(), requestBoardDto);

        assertThat(boardRepository.findById(saveBoardId))
                .isNotEmpty();
    }

    @DisplayName("게시글 id 조회")
    @Test
    void findBoardById() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("test title");
        requestBoardDto.setContent("test content");

        Long saveBoardId = boardService.saveBoard(userA.getId(), requestBoardDto);

        ResponseBoardDto boardDto = boardService.findBoardById(saveBoardId);
        assertThat(boardDto.getTitle())
                .isEqualTo(requestBoardDto.getTitle());
    }

    @DisplayName("존재하지 않는 게시글 id 조회시 예외 발생")
    @Test
    void findBoardByIdException() {
        assertThatThrownBy(() -> boardService.findBoardById(-1L))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 페이징 조회")
    @Test
    void findBoardPage() {
        saveBoard(10);

        Page<BoardListDto> boardpage = boardService.findBoardList(PageRequest.of(0, 10));
        assertThat(boardpage.getTotalElements())
                .isEqualTo(10L);
    }

    @DisplayName("게시글 작성자 이름 검색")
    @Test
    void findBoardByUsername() {
        saveBoard(5);

        Page<BoardListDto> boardPageByUsername = boardService.findBoardListByUsername(userA.getUsername(), PageRequest.of(0, 10));

        assertThat(boardPageByUsername.getTotalElements())
                .isEqualTo(5L);
    }

    @DisplayName("게시글 제목 검색")
    @Test
    void findBoardByTitle() {
        saveBoard(5);

        String title = "test title 1";
        Page<BoardListDto> boardPageByTitle = boardService.findBoardListByTitle(title, PageRequest.of(0, 10));

        assertThat(boardPageByTitle.getTotalElements())
                .isEqualTo(1L);
    }

    @DisplayName("게시글 내용 검색")
    @Test
    void findBoardByContent() {
        saveBoard(10);

        String content = "content 5";
        Page<BoardListDto> boardPageByContent = boardService.findBoardByContent(content, PageRequest.of(0, 10));

        assertThat(boardPageByContent.getTotalElements())
                .isEqualTo(1L);
    }

    @DisplayName("게시글 수정")
    @Test
    void editBoard() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");
        Long saveBoardId = boardService.saveBoard(userA.getId(), requestBoardDto);

        String updateTitle = "update title";
        EditBoardDto editBoardDto = new EditBoardDto(saveBoardId, updateTitle, "update content");
        Long editedBoardId = boardService.editBoard(saveBoardId, editBoardDto, userA.getId());

        assertThat(boardRepository.findById(editedBoardId).get().getTitle())
                .isEqualTo(updateTitle);
    }

    @DisplayName("게시글 삭제")
    @Test
    void deleteBoard() {
        RequestBoardDto requestBoardDto = new RequestBoardDto();
        requestBoardDto.setTitle("title");
        requestBoardDto.setContent("content");
        Long saveBoardId = boardService.saveBoard(userA.getId(), requestBoardDto);

        assertDoesNotThrow(() -> boardService.deleteBoard(saveBoardId, userA.getId()));
    }

    private void saveBoard(Integer boardQuantity) {
        for (int i = 0; i < boardQuantity; i++) {
            Board board = Board.of(userA, "test title " + (i + 1), "test content " + (i + 1));
            boardRepository.save(board);
        }
        log.info("=======================================================================");
        log.info("============================= save board ==============================");
        log.info("=======================================================================");
    }
}