package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.SignupDto;
import song.spring4.entity.User;
import song.spring4.exception.notfoundexception.UserNotFoundException;
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
    public Long updateUsername(Long id, String username) {
        User findUser = getById(id);

        validUpdateParam(username);

        findUser.setUsername(username);

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public Long updateName(Long id, String name) {
        User findUser = getById(id);

        validUpdateParam(name);

        findUser.setName(name);

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public Long updatePassword(Long id, String password) {
        User findUser = getById(id);

        validUpdateParam(password);

        findUser.setPassword(passwordEncoder.encode(password));

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
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
        return userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("사용자를 찾을 수 없습니다.")
        );
    }

    private void validUpdateParam(String str) {
        if (str == null && "".equals(str)) {
            throw new IllegalArgumentException("입력값이 잘못되었습니다.");
        }
    }
}
