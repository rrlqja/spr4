package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.repository.UserRoleJpaRepository;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class UserRoleServiceTest {
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRoleJpaRepository userRoleRepository;

    @BeforeEach
    void beforeEach() {
        userRoleRepository.deleteAll();
    }

    @DisplayName("권한 부여")
    @Test
    void grantRoleTest() {
        Long userId = 1L;
        userRoleService.grantRole(userId, Role.ROLE_USER.name());

        Assertions.assertThat(userRoleRepository.findByUserIdAndRole(userId, Role.ROLE_USER))
                .isNotEmpty();
    }

    @DisplayName("권한 회수")
    @Test
    void revokeRoleTest() {
        Long userId = 1L;
        // grant role
        userRoleService.grantRole(userId, Role.ROLE_USER.name());
        Assertions.assertThat(userRoleRepository.findByUserIdAndRole(userId, Role.ROLE_USER))
                .isNotEmpty();

        // revoke role
        userRoleService.revokeRole(userId, Role.ROLE_USER.name());
        Assertions.assertThat(userRoleRepository.findByUserIdAndRole(userId, Role.ROLE_USER))
                .isEmpty();
    }

}