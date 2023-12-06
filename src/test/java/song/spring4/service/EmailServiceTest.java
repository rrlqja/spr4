package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.domain.account.service.EmailService;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void email1() {
//        Assertions.assertThat(emailService.sendSimpleMessage("dkclasltmf@naver.com", "test sub", "test").getToEmail().length)
//                .isEqualTo(1);
    }
}