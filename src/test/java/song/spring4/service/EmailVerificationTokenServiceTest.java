package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.EmailVerificationToken;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class EmailVerificationTokenServiceTest {

    @Autowired
    private EmailVerificationTokenService emailVerificationTokenService;

    @Test
    void save1() {
        assertThat(emailVerificationTokenService.createToken("email"))
                .isEqualTo(emailVerificationTokenService.findById(1L).getToken());
    }

    @Test
    void verify1() {
        String token = emailVerificationTokenService.createToken("email");

        Long tokenId = emailVerificationTokenService.verify(token);

        EmailVerificationToken findToken = emailVerificationTokenService.findById(tokenId);
        assertThat(findToken.isVerified()).isTrue();
    }

}