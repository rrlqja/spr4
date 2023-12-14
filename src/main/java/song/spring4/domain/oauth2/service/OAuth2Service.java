package song.spring4.domain.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.domain.userrole.service.UserRoleService;
import song.spring4.domain.oauth2.entity.Sns;
import song.spring4.domain.oauth2.repository.SnsJpaRepository;
import song.spring4.domain.user.repository.UserJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private final SnsJpaRepository snsRepository;
    private final UserJpaRepository userRepository;
    private final UserRoleService userRoleService;

    @Transactional
    public Sns findOrCreate(String snsId, String snsName, String snsEmail) {
        Optional<Sns> findProvider = snsRepository.findBySnsEmail(snsEmail);
        if (findProvider.isEmpty()) {
            User newUser = userRepository.findByEmail(snsEmail)
                    .orElseGet(() -> userRepository.save(
                            User.of(generateUsername(), null, snsName, snsEmail)));
            userRoleService.grantRole(newUser.getId(), Role.ROLE_USER.name());

            Sns sns = new Sns(snsId, snsName, snsEmail, newUser);
            return snsRepository.save(sns);
        }
        return findProvider.get();
    }

    private String generateUsername() {
        return "user" + UUID.randomUUID().toString().substring(0, 7);
    }
}
