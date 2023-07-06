package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.exception.notfoundexception.TokenNotFoundException;
import song.spring4.repository.ResetPasswordTokenJpaRepository;

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

        resetPasswordService.validToken(token);

        assertThatThrownBy(() -> resetPasswordService.validToken(token + "x"))
                .isInstanceOf(TokenNotFoundException.class);
    }

    @Test
    void get1() {
        String token = resetPasswordService.createPasswordToken("d");

        String userEmail = resetPasswordService.getUserEmail(token);
        assertThat(userEmail).isEqualTo("d");
    }
}