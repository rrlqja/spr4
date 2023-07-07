package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import song.spring4.dto.FindPasswordDto;
import song.spring4.dto.FindUsernameDto;
import song.spring4.dto.ResponseUsername;
import song.spring4.dto.SignupDto;
import song.spring4.entity.User;
import song.spring4.exception.AlreadyExistsUsernameException;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(SignupDto signupDto) {
        User user = signupDto.toEntity();
        user.encodePassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Transactional
    public Long updateUsername(Long id, String username) {
        User findUser = getById(id);
        validUsername(username);

        findUser.setUsername(username);

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public void validUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalRequestArgumentException("입력값을 확인해주세요.");
        }
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new AlreadyExistsUsernameException("이미 존재하는 Username 입니다.");
        }
    }

    @Transactional
    public Long updatePassword(Long id, String password) {
        User findUser = getById(id);

        findUser.setPassword(passwordEncoder.encode(password));

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public Long updateName(Long id, String name) {
        User findUser = getById(id);

        findUser.setName(name);

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public Long updateEmail(Long id, String email) {
        User findUser = getById(id);

        findUser.setEmail(email);

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
    }

    @Transactional
    public User findUserById(Long id) {
        User findUser = getById(id);

        return findUser;
    }

    @Transactional
    public User findUserByUsername(String username) {
        User findUser = getByUsername(username);

        return findUser;
    }

    @Transactional
    public ResponseUsername findUsername(FindUsernameDto findUsernameDto) {
        User finsUser = userRepository.findByNameAndEmail(findUsernameDto.getName(), findUsernameDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        ResponseUsername responseUsername = new ResponseUsername();
        responseUsername.setUsername(finsUser.getUsername());
        return responseUsername;
    }

    @Transactional
    public String findPassword(FindPasswordDto findPasswordDto) {
        User findUser = userRepository.findByUsernameAndNameAndEmail(findPasswordDto.getUsername(),
                        findPasswordDto.getName(), findPasswordDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        return findUser.getEmail();
    }

    @Transactional
    public Long resetPassword(String email, String newPassword) {
        User findUser = getByEmail(email);

        findUser.setPassword(passwordEncoder.encode(newPassword));

        User updateUser = userRepository.save(findUser);
        return updateUser.getId();
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

    private User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
