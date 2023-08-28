package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.Role;
import song.spring4.entity.User;
import song.spring4.entity.UserRole;
import song.spring4.entity.role.RoleName;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.repository.UserJpaRepository;
import song.spring4.repository.UserRoleJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final RoleService roleService;
    private final UserRoleJpaRepository userRoleRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public void grantRole(Long userId, RoleName roleName) {
        Optional<UserRole> findUserRole = userRoleRepository.findByUserIdAndRoleName(userId, roleName);
        if (findUserRole.isPresent()) {
            log.info("이미 부여된 권한입니다. id = {} role = {}", userId, roleName);
            return;
        }

        User findUser = getUserById(userId);
        Role role = roleService.findOrCreate(roleName);

        UserRole userRole = new UserRole(role);
        findUser.addUserRole(userRole);

        UserRole saveUserRole = userRoleRepository.save(userRole);
    }

    @Transactional
    public void revokeRole(Long userId, RoleName roleName) {
        Optional<UserRole> findUserRole = userRoleRepository.findByUserIdAndRoleName(userId, roleName);
        findUserRole.ifPresent(userRole -> {
            userRoleRepository.delete(userRole);
            userRole.getUser().getRoleList().remove(userRole);
        });
    }

    private User getUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return findUser.get();
    }
}
