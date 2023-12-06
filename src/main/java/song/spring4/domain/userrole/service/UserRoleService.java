package song.spring4.domain.userrole.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.User;
import song.spring4.domain.userrole.UserRole;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.domain.user.repository.UserJpaRepository;
import song.spring4.domain.userrole.repository.UserRoleJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleJpaRepository userRoleRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public void grantRole(Long userId, String roleName) {
        Role role = Role.valueOf(roleName);

        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRole(userId, role);
        if (optionalUserRole.isPresent()) {
            log.info("이미 부여된 권한입니다. id = {} role = {}", userId, roleName);
            return;
        }

        User user = getUserById(userId);
        UserRole userRole = UserRole.of(user, role);

        userRoleRepository.save(userRole);
    }

    @Transactional
    public void revokeRole(Long userId, String roleName) {
        Role role = Role.valueOf(roleName);
        userRoleRepository.findByUserIdAndRole(userId, role)
                .ifPresent(userRole -> {
                    userRoleRepository.delete(userRole);
                    userRole.getUser().getRoleList().remove(userRole);
                });
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
