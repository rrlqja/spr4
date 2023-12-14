package song.spring4.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.common.dto.SignupDto;
import song.spring4.domain.user.entity.User;
import song.spring4.exception.already.exceptions.AlreadyExistsUsernameException;
import song.spring4.exception.invalid.exceptions.InvalidUserException;
import song.spring4.exception.notfound.exceptions.UserNotFoundException;
import song.spring4.domain.user.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입")
    @Test
    void join() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUserA");
        signupDto.setPassword("testPassword");

        assertDoesNotThrow(() -> userService.join(signupDto));
    }

    @DisplayName("동일한 username이 회원가입시 예외 발생")
    @Test
    void joinExceptionTest() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUserA");
        signupDto.setPassword("testPassword");

        assertThatThrownBy(()-> userService.join(signupDto))
                .isInstanceOf(AlreadyExistsUsernameException.class);
    }

    @DisplayName("username 변경")
    @Test
    void updateUsernameTest() {
        Long userId = 1L;
        String newUsername = "testNewUsername";
        userService.updateUsername(userId, newUsername);

        User user = userRepository.findById(userId).get();
        assertThat(user.getUsername())
                .isEqualTo(newUsername);
    }

    @DisplayName("비밀번호 변경")
    @Test
    void updatePasswordTest() {
        Long userId = 1L;
        String originalPassword = "a";
        String newPassword = "newPassword";

        userService.updatePassword(userId, originalPassword, newPassword);

        User user = userRepository.findById(userId).get();

        assertThat(passwordEncoder.matches(newPassword, user.getPassword()))
                .isTrue();
    }

    @DisplayName("기존 비밀번호와 새로운 비밀번호가 동일하면 예외 발생")
    @Test
    void updatePasswordExceptionTest() {
        Long userId = 1L;
        String originalPassword = "a";
        String newPassword = "a";

        assertThatThrownBy(() -> userService.updatePassword(userId, originalPassword, newPassword))
                .isInstanceOf(InvalidUserException.class);
    }

    @DisplayName("이름 변경")
    @Test
    void updateNameTest() {
        Long userId = 1L;
        String newName = "testNewName";

        userService.updateName(userId, newName);

        User user = userRepository.findById(userId).get();
        assertThat(user.getName())
                .isEqualTo(newName);
    }

    @DisplayName("사용자 조회 예외")
    @Test
    void find1() {
        assertThatThrownBy(() -> userService.findUserById(-1L)).isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("사용자 탈퇴")
    @Test
    void delete() {
        Long userId = 1L;
        userService.deleteUserById(userId);

        User user = userRepository.findById(userId).get();
        assertThat(user.isEnabled())
                .isFalse();
    }
}