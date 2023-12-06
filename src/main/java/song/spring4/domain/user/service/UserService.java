package song.spring4.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import song.spring4.domain.common.dto.SignupDto;
import song.spring4.domain.user.User;
import song.spring4.domain.user.dto.ResponseUserDto;
import song.spring4.exception.AlreadyExistsUsernameException;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.domain.user.repository.UserJpaRepository;
import song.spring4.security.pricipal.UserPrincipal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(SignupDto signupDto) {
        validateUsername(signupDto.getUsername());
        User user = User.of(signupDto.getUsername(), passwordEncoder.encode(signupDto.getPassword()), signupDto.getName(), signupDto.getEmail());

        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Transactional
    public Long updateUsername(Long userId, String newUsername) {
        validateUsername(newUsername);

        User user = getById(userId);

        user.updateUsername(newUsername);
        User updateUser = userRepository.save(user);

        updateSecurityContext(updateUser);

        return updateUser.getId();
    }

    @Transactional
    public void validateUsername(String username) {
        hasText(username);

        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new AlreadyExistsUsernameException("이미 존재하는 Username 입니다.");
                });
    }

    @Transactional
    public Long updatePassword(Long userId, String originalPassword, String newPassword) {
        User user = getById(userId);

        validatePassword(originalPassword, newPassword, user.getPassword());

        user.updatePassword(passwordEncoder.encode(newPassword));
        User updateUser = userRepository.save(user);

        updateSecurityContext(updateUser);

        return updateUser.getId();
    }

    @Transactional
    public Long updateName(Long userId, String newName) {
        User user = getById(userId);

        user.updateName(newName);
        User updateUser = userRepository.save(user);

        updateSecurityContext(updateUser);

        return updateUser.getId();
    }

    @Transactional
    public Long updateEmail(Long userId, String newEmail) {
        User user = getById(userId);

        user.updateEmail(newEmail);
        User updateUser = userRepository.save(user);

        updateSecurityContext(updateUser);

        return updateUser.getId();
    }

    @Transactional
    public ResponseUserDto findUserById(Long userId) {
        User user = getById(userId);

        return new ResponseUserDto(user);
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

    private void hasText(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalRequestArgumentException("입력값을 확인해주세요.");
        }
    }

    private void validatePassword(String originalPassword, String newPassword, String userPassword) {
        validateOriginalPassword(originalPassword, userPassword);
        validateNewPassword(originalPassword, newPassword);
    }

    private void validateOriginalPassword(String originalPassword, String currentPassword) {
        if (!passwordEncoder.matches(originalPassword, currentPassword)) {
            throw new IllegalRequestArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }
    private void validateNewPassword(String originalPassword, String newPassword) {
        if (originalPassword.equals(newPassword)) {
            throw new IllegalRequestArgumentException("동일한 비밀번호로 변경할 수 없습니다.");
        }
    }

    private void updateSecurityContext(User updateUser) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = UserPrincipal.of(updateUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
        context.setAuthentication(authenticationToken);
    }
}
