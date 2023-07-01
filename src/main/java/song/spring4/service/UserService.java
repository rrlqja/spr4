package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.SignupDto;
import song.spring4.entity.User;
import song.spring4.exception.UserNotFoundException;
import song.spring4.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long saveUser(SignupDto signupDto) {
        User user = signupDto.toEntity();
        user.encodePassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Transactional
    public User findUserById(Long id) {
        User findUser = getById(id);

        return findUser;
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()->{
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");});
    }
}
