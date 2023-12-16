package song.spring4.domain.email.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.account.service.EmailService;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendEmail() {
        assertDoesNotThrow(() -> emailService.sendSimpleMessage("dkclasltmf@naver.com", "test sub", "test"));
    }
}