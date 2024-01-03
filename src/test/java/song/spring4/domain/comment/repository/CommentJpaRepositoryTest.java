package song.spring4.domain.comment.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class CommentJpaRepositoryTest {
    @Autowired
    CommentJpaRepository commentRepository;

    @Test
    void find() {
        Assertions.assertDoesNotThrow(() -> commentRepository.findById(1L));
    }
}