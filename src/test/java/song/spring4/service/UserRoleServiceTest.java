package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.UserRoleJpaRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class UserRoleServiceTest {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRoleJpaRepository userRoleRepository;

    @Test
    void save1() {
//        userRoleService.grantRole(1L, RoleName.ROLE_ADMIN.name());
//
//        assertThat(userRoleRepository.findByUserIdAndRole(1L, RoleName.ROLE_ADMIN).isPresent()).isTrue();
//
//        userRoleService.revokeRole(1L, RoleName.ROLE_ADMIN);
//
//        assertThat(userRoleRepository.findByUserIdAndRole(1L, RoleName.ROLE_ADMIN).isEmpty()).isTrue();
    }

}