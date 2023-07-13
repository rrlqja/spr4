package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.entity.Role;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.RoleJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class RoleServiceTest {

    @Autowired
    RoleService roleService;
    @Autowired
    RoleJpaRepository roleRepository;

    @Test
    void save1() {
        assertThat(roleService.findOrCreate(RoleName.ROLE_ADMIN).getRoleName()).isEqualTo(RoleName.ROLE_ADMIN);
    }

    @Test
    void save2() {
        roleService.findOrCreate(RoleName.ROLE_ADMIN);
        roleService.findOrCreate(RoleName.ROLE_USER);
        roleService.findOrCreate(RoleName.ROLE_USER);
        roleService.findOrCreate(RoleName.ROLE_USER);

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList.size()).isEqualTo(2);
    }

}