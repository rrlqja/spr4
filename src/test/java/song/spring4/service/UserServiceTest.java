package song.spring4.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.SignupDto;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(value = false)
    void save1() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("1");
        signupDto.setPassword("1");

        Long id = userService.saveUser(signupDto);
    }
}