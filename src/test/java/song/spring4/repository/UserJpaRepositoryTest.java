package song.spring4.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.comment.repository.CommentJpaRepository;
import song.spring4.domain.file.repository.FileEntityJpaRepository;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.user.repository.UserJpaRepository;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserJpaRepositoryTest {
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    BoardJpaRepository boardJpaRepository;
    @Autowired
    CommentJpaRepository commentRepository;
    @Autowired
    FileEntityJpaRepository fileEntityRepository;

    @BeforeEach
    void beforeEach() {
        fileEntityRepository.deleteAll();
        commentRepository.deleteAll();
        boardJpaRepository.deleteAll();
        userRepository.deleteAll();
        log.info("=======================================================================");
        log.info("============================= before each =============================");
        log.info("=======================================================================");
    }

    @DisplayName("회원 저장")
    @Test
    void joinTest() {
        User user = User.of("testUsername", "testP", "testName", "testEmail@email.com");

        Assertions.assertDoesNotThrow(() -> userRepository.save(user));
    }

    @DisplayName("username 검색")
    @Test
    void findUsernameTest() {
        String username = "testUsername";
        User user = User.of(username, "testP", "testName", "testEmail@email.com");
        userRepository.save(user);

        User findUser = userRepository.findByUsername(username).get();

        org.assertj.core.api.Assertions.assertThat(findUser.getUsername())
                .isEqualTo(user.getUsername());
    }
}