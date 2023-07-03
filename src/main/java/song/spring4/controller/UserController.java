package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import song.spring4.dto.FindPasswordDto;
import song.spring4.dto.UserDto;
import song.spring4.entity.User;
import song.spring4.mapper.UserMapper;
import song.spring4.service.EmailVerificationTokenService;
import song.spring4.service.UserService;
import song.spring4.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @ResponseBody
    @GetMapping("/user")
    public UserDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          Model model) {
        User findUser = userService.findUserById(userDetails.getId());
        UserDto userDto = UserMapper.toUserDto(findUser);

        model.addAttribute("userDto", userDto);
        return userDto;
    }

    @GetMapping("/user/findPassword")
    public void getFindPassword(@RequestParam FindPasswordDto findPasswordDto) {
        String email = userService.findPassword(findPasswordDto);
        emailVerificationTokenService.createToken(email);

    }
}
