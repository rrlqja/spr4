package song.spring4;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.User;
import song.spring4.repository.UserJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void doInit() {
        initService.init1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        private final UserJpaRepository userRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public void init1() {
            User user = new User();
            user.setUsername("u1");
            user.setPassword(passwordEncoder.encode("1"));
            user.setName("n1");
            user.setEmail("init@email.com");
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            userRepository.save(user);
        }
    }
}
