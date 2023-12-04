package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.User;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.entity.oauth2.Sns;
import song.spring4.repository.SnsJpaRepository;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {
    private final SnsJpaRepository providerRepository;
    private final UserJpaRepository userRepository;
    private final UserRoleService userRoleService;

    @Transactional
    public Sns findOrCreate(String snsId, String snsName, String snsEmail) {
        Optional<Sns> findProvider = providerRepository.findBySnsId(snsId);
        if (findProvider.isEmpty()) {
            User newUser = userRepository.findByEmail(snsEmail)
                    .orElseGet(() -> userRepository.save(
                            User.of(generateUsername(), null, snsName, snsEmail)));
            userRoleService.grantRole(newUser.getId(), Role.ROLE_USER.name());

            Sns sns = new Sns(snsId, snsName, snsEmail, newUser);
            return providerRepository.save(sns);
        }
        return findProvider.get();
    }

    private String generateUsername() {
        return "user" + UUID.randomUUID().toString().substring(0, 7);
    }
}
