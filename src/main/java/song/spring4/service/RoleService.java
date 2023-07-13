package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.Role;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.RoleJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleJpaRepository roleRepository;

    @Transactional
    public Role findOrCreate(RoleName roleName) {
        Optional<Role> findRole = roleRepository.findByRoleName(roleName);
        if (findRole.isPresent()) {
            return findRole.get();
        }
        Role role = new Role();
        role.setRoleName(roleName);
        Role saveRole = roleRepository.save(role);
        return saveRole;
    }
}
