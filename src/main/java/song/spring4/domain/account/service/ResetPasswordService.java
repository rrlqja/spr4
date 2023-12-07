package song.spring4.domain.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.account.ResetPasswordToken;
import song.spring4.exception.notfound.exceptions.TokenNotFoundException;
import song.spring4.domain.account.repository.ResetPasswordTokenJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetPasswordService {
    private final ResetPasswordTokenJpaRepository resetPasswordTokenRepository;

    @Transactional
    public String createPasswordToken(String email) {
        Optional<ResetPasswordToken> optionalPasswordToken = resetPasswordTokenRepository.findByEmail(email);
        if (optionalPasswordToken.isPresent()) {
            ResetPasswordToken passwordToken = optionalPasswordToken.get();
            passwordToken.updateToken(UUID.randomUUID().toString().substring(0, 5));
            ResetPasswordToken saveToken = resetPasswordTokenRepository.save(passwordToken);
            return saveToken.getToken();
        }

        ResetPasswordToken passwordToken = ResetPasswordToken.of(email, UUID.randomUUID().toString().substring(0, 5));
        ResetPasswordToken saveToken = resetPasswordTokenRepository.save(passwordToken);
        return saveToken.getToken();
    }

    @Transactional
    public void validateToken(String token) {
        resetPasswordTokenRepository.findByToken(token).orElseThrow(() ->
                new TokenNotFoundException("토큰을 찾을 수 없습니다."));
    }

    @Transactional
    public String getUserEmail(String token) {
        ResetPasswordToken findPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("토큰을 찾을 수 없습니다."));
        return findPasswordToken.getEmail();

    }

    @Transactional
    public void deleteToken(String token) {
        Optional<ResetPasswordToken> findToken = resetPasswordTokenRepository.findByToken(token);
        if (findToken.isPresent()) {
            ResetPasswordToken resetPasswordToken = findToken.get();
            resetPasswordTokenRepository.delete(resetPasswordToken);
        }
    }
}
