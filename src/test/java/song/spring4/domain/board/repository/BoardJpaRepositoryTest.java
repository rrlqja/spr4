package song.spring4.domain.board.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.file.repository.FileEntityJpaRepository;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.user.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class BoardJpaRepositoryTest {
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
    void saveTest() {
        Board board = Board.of(userA, "test title", "test content");

        assertDoesNotThrow(() -> boardRepository.save(board));
    }

    @DisplayName("게시글 조회")
    @Test
    void findBoardTest() {
        String title = "test title";
        Board saveBoard = boardRepository.save(Board.of(userA, title, "test content"));

        Board findBoard = boardRepository.findById(saveBoard.getId()).get();

        assertThat(findBoard.getUser().getUsername())
                .isEqualTo(userA.getUsername());
        assertThat(findBoard.getTitle())
                .isEqualTo(title);
    }

    @Test
    void findAllTest() {
        boardRepository.findAll(PageRequest.of(0, 10));
    }

    @Test
    void findAllUsernameLikeTest() {
        boardRepository.findAllByUsernameLike("user", PageRequest.of(0, 10));
    }

    @DisplayName("게시글 리스트 조회")
    @Test
    void findBoardListTest() {
        saveBoard(100);
        Page<Board> boardPage = boardRepository.findAll(PageRequest.of(0, 10));

        assertThat(boardPage.getTotalElements())
                .isEqualTo(100);
        assertThat(boardPage.getTotalPages())
                .isEqualTo(10);
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