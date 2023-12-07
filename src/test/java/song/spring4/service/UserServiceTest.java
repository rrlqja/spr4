package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.service.UserService;
import song.spring4.domain.common.dto.SignupDto;
import song.spring4.domain.user.entity.User;
import song.spring4.exception.already.exceptions.AlreadyExistsUsernameException;
import song.spring4.exception.invalid.exceptions.IllegalArgumentException;
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

    @DisplayName("회원가입 테스트")
    @Test
    void joinTest1() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUserA");
        signupDto.setPassword("testPassword");

        assertDoesNotThrow(() -> userService.join(signupDto));
    }

    @DisplayName("회원가입 username 검증 테스트")
    @Test
    void joinTest2() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUserA");
        signupDto.setPassword("testPassword");

        Long joinId = userService.join(signupDto);

        User user = userRepository.findById(joinId).get();
        assertThat(user.getUsername())
                .isEqualTo(signupDto.getUsername());
    }

    @DisplayName("동일한 username이 회원가입시 예외 발생")
    @Test
    void joinExceptionTest() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUserA");
        signupDto.setPassword("testPassword");

        userService.join(signupDto);

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

    @DisplayName("username 변경시 동일한 username이 존재하면 예외 발생")
    @Test
    void updateUsernameExceptionTest() {
        Long userId = 1L;
        String newUsername = "testNewUsername";
        userService.updateUsername(userId, newUsername);

        assertThatThrownBy(() -> userService.updateUsername(userId, newUsername))
                .isInstanceOf(AlreadyExistsUsernameException.class);
    }

    @DisplayName("비밀번호 변경")
    @Test
    void updatePasswordTest() {
        Long userId = 1L;
        String originalPassword = "a";
        String newPassword = "newA";

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
                .isInstanceOf(IllegalArgumentException.class);
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

    @Test
    void find1() {
        assertThatThrownBy(() -> userService.findUserById(-1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @Transactional
    void update1() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("username");
        signupDto.setPassword("password");
        signupDto.setName("name");
        Long saveId = userService.join(signupDto);

        Long updateUsernameId = userService.updateUsername(saveId, "new_username");
        Long updatePasswordId = userService.updatePassword(saveId, "password", "new_password");
        Long updateNameId = userService.updateName(saveId, "new_name");

//        em.flush();
//        em.clear();
//
//        User findUser = userService.findUserById(saveId);
//        assertThat(findUser.getUsername()).isEqualTo("new_username");
//        assertThat(passwordEncoder.matches("new_password", findUser.getPassword())).isTrue();
//        assertThat(findUser.getName()).isEqualTo("new_name");
    }

    @Test
    void update2() {
        Long id = userService.updateUsername(1L, "updateUsername");
    }

    @Test
    void delete1() {
        userService.deleteUserById(1L);

        assertThatThrownBy(() -> userService.findUserById(1L)).isInstanceOf(UserNotFoundException.class);
    }
}