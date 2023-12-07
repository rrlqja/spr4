package song.spring4.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.board.repository.BoardJpaRepository;
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

    @DisplayName("게시글 업데이트")
    @Test
    void updateTest() {
        Board board = Board.of(userA, "test title", "test content");
        Board saveBoard = boardRepository.save(board);

        String updateTitle = "updateTitle";
        Board testBoard = boardRepository.findById(saveBoard.getId()).get();
        testBoard.updateBoard(updateTitle, "updateContent");
        Board updateBoard = boardRepository.save(testBoard);

        Board findBoard = boardRepository.findById(updateBoard.getId()).get();
        assertThat(findBoard.getTitle())
                .isEqualTo(updateTitle);
    }
}