package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.User;
import song.spring4.entity.oauth2.Provider;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.ProviderJpaRepository;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderJpaRepository providerRepository;
    private final UserJpaRepository userRepository;
    private final UserRoleService userRoleService;

    @Transactional
    public Provider findOrCreate(String snsId, String snsName, String snsEmail) {
        Optional<Provider> findProvider = providerRepository.findBySnsId(snsId);
        if (findProvider.isEmpty()) {
            User newUser = userRepository.findByEmail(snsEmail)
                    .orElseGet(() -> userRepository.save(new User(null, null, snsName, snsEmail, true, true, true, true)));
            userRoleService.grantRole(newUser.getId(), RoleName.ROLE_USER);

            Provider provider = new Provider(snsId, snsName, snsEmail, newUser);
            return providerRepository.save(provider);
        }
        return findProvider.get();
    }
}
