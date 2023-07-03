package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.spring4.entity.FindPasswordToken;
import song.spring4.repository.FindPasswordTokenJpaRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindPasswordService {
    private final FindPasswordTokenJpaRepository findPasswordTokenRepository;

    public void createToken(String email) {
        FindPasswordToken findPasswordToken = new FindPasswordToken();
        findPasswordToken.setEmail(email);
        findPasswordToken.setToken(UUID.randomUUID().toString().substring(0, 5));
    }
}
