package song.spring4.domain.account.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.domain.account.service.ResetPasswordService;
import song.spring4.exception.notfound.exceptions.TokenNotFoundException;
import song.spring4.domain.account.repository.ResetPasswordTokenJpaRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class ResetPasswordServiceTest {
    @Autowired
    ResetPasswordService resetPasswordService;
    @Autowired
    ResetPasswordTokenJpaRepository resetPasswordTokenRepository;

    @Test
    void create1() {
        String token = resetPasswordService.createPasswordToken("d");

        assertThat(resetPasswordTokenRepository.findByToken(token).get().getEmail())
                .isEqualTo("d");
    }

    @Test
    void valid1() {
        String token = resetPasswordService.createPasswordToken("d");

        resetPasswordService.validateToken(token);

        assertThatThrownBy(() -> resetPasswordService.validateToken(token + "x"))
                .isInstanceOf(TokenNotFoundException.class);
    }

    @Test
    void get1() {
        String token = resetPasswordService.createPasswordToken("d");

        String userEmail = resetPasswordService.getUserEmail(token);
        assertThat(userEmail).isEqualTo("d");
    }
}