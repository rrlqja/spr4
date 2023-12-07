package song.spring4.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.user.repository.UserJpaRepository;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userRepository;

    @Test
    void save1() {
        User user = User.builder()
                .username("u1")
                .build();

        userRepository.save(user);
    }

}