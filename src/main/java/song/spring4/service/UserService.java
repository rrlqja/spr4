package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import song.spring4.userdetails.UserDetailsImpl;

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
    public Long updateUsername(Long userId, String username) {
        User findUser = getById(userId);
        validUsername(username);

        findUser.setUsername(username);

        updateSecurityContext(findUser);

        return findUser.getId();
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
    public Long updatePassword(Long userId, String origPassword, String newPassword) {
        User findUser = getById(userId);

        validOrigPassword(origPassword, findUser.getPassword());

        findUser.setPassword(passwordEncoder.encode(newPassword));

        updateSecurityContext(findUser);

        return findUser.getId();
    }

    @Transactional
    public Long updateName(Long userId, String name) {
        User findUser = getById(userId);

        findUser.setName(name);

        updateSecurityContext(findUser);

        return findUser.getId();
    }

    @Transactional
    public Long updateEmail(Long userId, String email) {
        User findUser = getById(userId);

        findUser.setEmail(email);

        updateSecurityContext(findUser);

        return findUser.getId();
    }

    @Transactional
    public User findUserById(Long userId) {
        User findUser = getById(userId);

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
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
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

    private void validOrigPassword(String origPassword, String currentPassword) {
        if (!passwordEncoder.matches(origPassword, currentPassword)) {
            throw new IllegalRequestArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    private void updateSecurityContext(User updateUser) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsImpl userDetails = new UserDetailsImpl(updateUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        context.setAuthentication(authenticationToken);
    }
}
