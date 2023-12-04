package song.spring4.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.SignupDto;
import song.spring4.domain.user.User;
import song.spring4.exception.notfoundexception.UserNotFoundException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @Rollback(value = false)
    void save1() {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("1");
        signupDto.setPassword("1");

        Long id = userService.join(signupDto);
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

        em.flush();
        em.clear();

        User findUser = userService.findUserById(saveId);
        assertThat(findUser.getUsername()).isEqualTo("new_username");
        assertThat(passwordEncoder.matches("new_password", findUser.getPassword())).isTrue();
        assertThat(findUser.getName()).isEqualTo("new_name");
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