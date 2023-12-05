package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.user.User;
import song.spring4.dto.userdto.ResponseUsername;
import song.spring4.entity.EmailVerificationToken;
import song.spring4.entity.ResetPasswordToken;
import song.spring4.exception.AlreadyExistsEmailException;
import song.spring4.exception.notfoundexception.TokenNotFoundException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.repository.EmailVerificationJpaRepository;
import song.spring4.repository.ResetPasswordTokenJpaRepository;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;
    private final ResetPasswordTokenJpaRepository resetPasswordTokenRepository;
    private final EmailVerificationJpaRepository emailVerificationRepository;
    private final EmailService emailService;

    @Transactional
    public ResponseUsername findUsername(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        return new ResponseUsername(user.getUsername());
    }

    @Transactional
    public void createResetPasswordToken(String username, String name, String email) {
        userRepository.findByUsernameAndNameAndEmail(username, name, email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        ResetPasswordToken passwordToken = createPasswordToken(email);

        emailService.sendSimpleMessage(email, "reset password",
                "localhost:8080/account/resetPassword/" + passwordToken.getToken());
    }

    @Transactional
    public void validateRestPasswordToken(String token) {
        getPasswordToken(token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        ResetPasswordToken passwordToken = getPasswordToken(token);
        User user = getUser(passwordToken.getEmail());

        user.updatePassword(passwordEncoder.encode(newPassword));
        resetPasswordTokenRepository.delete(passwordToken);
    }

    @Transactional
    public void createEmailVerificationToken(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new AlreadyExistsEmailException();
                });
        String token = createEmailToken(email);

        emailService.sendSimpleMessage(email, "verify email", "token: " + token);
    }

    @Transactional
    public void verify(String token) {
        Optional<EmailVerificationToken> optionalEmailToken = emailVerificationRepository.findByToken(token);
        if (optionalEmailToken.isEmpty()) {
            throw new TokenNotFoundException("토큰을 찾을 수 없습니다.");
        }

        emailVerificationRepository.delete(optionalEmailToken.get());
    }

    private ResetPasswordToken createPasswordToken(String email) {
        Optional<ResetPasswordToken> optionalPasswordToken = resetPasswordTokenRepository.findByEmail(email);
        if (optionalPasswordToken.isPresent()) {
            ResetPasswordToken passwordToken = optionalPasswordToken.get();
            passwordToken.updateToken(UUID.randomUUID().toString().substring(0, 5));
            return resetPasswordTokenRepository.save(passwordToken);
        }

        ResetPasswordToken passwordToken = ResetPasswordToken.of(email, UUID.randomUUID().toString().substring(0, 5));
        return resetPasswordTokenRepository.save(passwordToken);
    }

    private ResetPasswordToken getPasswordToken(String token) {
        return resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("토큰을 찾을 수 없습니다."));
    }

    private String createEmailToken(String email) {
        Optional<EmailVerificationToken> findToken = emailVerificationRepository.findByEmail(email);
        if (findToken.isPresent()) {
            EmailVerificationToken emailVerificationToken = findToken.get();
            emailVerificationToken.updateToken(UUID.randomUUID().toString().substring(0, 5));
            EmailVerificationToken saveToken = emailVerificationRepository.save(emailVerificationToken);
            return saveToken.getToken();
        }

        EmailVerificationToken emailVerificationToken = EmailVerificationToken.of(email, UUID.randomUUID().toString().substring(0, 5));
        EmailVerificationToken saveToken = emailVerificationRepository.save(emailVerificationToken);
        log.info("save EmailVerificationToken, id = {}", saveToken.getId());
        return saveToken.getToken();
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
