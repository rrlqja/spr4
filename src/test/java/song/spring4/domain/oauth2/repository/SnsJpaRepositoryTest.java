package song.spring4.domain.oauth2.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class SnsJpaRepositoryTest {
    @Autowired
    SnsJpaRepository snsJpaRepository;

    @Test
    void findBySnsEmail() {
        snsJpaRepository.findBySnsEmail("dd");
    }
}