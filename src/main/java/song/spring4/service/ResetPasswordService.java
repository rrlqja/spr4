package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.ResetPasswordToken;
import song.spring4.exception.TokenNotFoundException;
import song.spring4.repository.ResetPasswordTokenJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetPasswordService {
    private final ResetPasswordTokenJpaRepository resetPasswordTokenRepository;

    @Transactional
    public String createPasswordToken(String email) {
        Optional<ResetPasswordToken> findToken = resetPasswordTokenRepository.findByEmail(email);
        if (findToken.isPresent()) {
            ResetPasswordToken PasswordToken = findToken.get();
            PasswordToken.setToken(UUID.randomUUID().toString().substring(0, 5));
            ResetPasswordToken saveToken = resetPasswordTokenRepository.save(PasswordToken);
            return saveToken.getToken();
        }

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setEmail(email);
        resetPasswordToken.setToken(UUID.randomUUID().toString().substring(0, 5));
        ResetPasswordToken saveToken = resetPasswordTokenRepository.save(resetPasswordToken);
        return saveToken.getToken();
    }

    @Transactional
    public void validToken(String token) {
        resetPasswordTokenRepository.findByToken(token).orElseThrow(() ->
                new TokenNotFoundException("토큰을 찾을 수 없습니다."));
    }

    @Transactional
    public String getUserEmail(String token) {
        ResetPasswordToken findPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("토큰을 찾을 수 없습니다.")
                );
        return findPasswordToken.getEmail();

    }
}
