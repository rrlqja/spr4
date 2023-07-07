package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import song.spring4.entity.EmailVerificationToken;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.exception.TokenAlreadyVerifiedException;
import song.spring4.exception.notfoundexception.TokenNotFoundException;
import song.spring4.repository.EmailVerificationJpaRepository;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationTokenService {
    private final UserJpaRepository userRepository;
    private final EmailVerificationJpaRepository emailVerificationRepository;

    @Transactional
    public String createToken(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalRequestArgumentException("입력값을 확인해주세요.");
        }

        isAlreadyVerified(email);

        Optional<EmailVerificationToken> findToken = emailVerificationRepository.findByEmail(email);
        if (findToken.isPresent()) {
            EmailVerificationToken emailVerificationToken = findToken.get();
            if (emailVerificationToken.isVerified()) {
                throw new TokenAlreadyVerifiedException("이미 인증된 이메일입니다.");
            }

            emailVerificationToken.setToken(UUID.randomUUID().toString().substring(0, 5));
            EmailVerificationToken saveToken = emailVerificationRepository.save(emailVerificationToken);
            return saveToken.getToken();
        }

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setEmail(email);
        emailVerificationToken.setToken(UUID.randomUUID().toString().substring(0, 5));
        emailVerificationToken.setVerified(false);

        EmailVerificationToken saveToken = emailVerificationRepository.save(emailVerificationToken);
        log.info("save EmailVerificationToken, id = {}", saveToken.getId());
        return saveToken.getToken();
    }

    @Transactional
    public EmailVerificationToken findById(Long id) {
        EmailVerificationToken findEmailVerificationToken = getById(id);

        return findEmailVerificationToken;
    }

    @Transactional
    public Long verify(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalRequestArgumentException("입력값을 확인해주세요.");
        }

        EmailVerificationToken findToken = getByToken(token);
        findToken.setVerified(true);

        EmailVerificationToken saveToken = emailVerificationRepository.save(findToken);
        return saveToken.getId();
    }

    @Transactional
    public void delete(Long id) {
        emailVerificationRepository.deleteById(id);
    }

    private EmailVerificationToken getById(Long id) {
        return emailVerificationRepository.findById(id).orElseThrow(() ->
                new TokenNotFoundException("토큰을 찾을 수 없습니다.")
        );
    }

    private EmailVerificationToken getByToken(String token) {
        Optional<EmailVerificationToken> findToken = emailVerificationRepository.findByToken(token);
        if (findToken.isEmpty()) {
            throw new TokenNotFoundException("토큰을 찾을 수 없습니다.");
        }
        return findToken.get();
    }

    private void isAlreadyVerified(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new TokenAlreadyVerifiedException("이미 인증된 이메일입니다.");
        });
    }
}
