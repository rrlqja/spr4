package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.FindPasswordDto;
import song.spring4.dto.UserDto;
import song.spring4.entity.User;
import song.spring4.mapper.UserMapper;
import song.spring4.service.EmailService;
import song.spring4.service.ResetPasswordService;
import song.spring4.service.UserService;
import song.spring4.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final ResetPasswordService resetPasswordService;

    @ResponseBody
    @GetMapping
    public UserDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) {
        User findUser = userService.findUserById(userDetails.getId());
        UserDto userDto = UserMapper.toUserDto(findUser);

        model.addAttribute("userDto", userDto);
        return userDto;
    }

    @GetMapping("/findPassword")
    public String getFindPassword(Model model) {
        model.addAttribute("findPasswordDto", new FindPasswordDto());

        return "user/findPassword";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/findPassword")
    public String postFindPassword(@ModelAttribute FindPasswordDto findPasswordDto) {
        String email = userService.findPassword(findPasswordDto);
        String token = resetPasswordService.createPasswordToken(email);
        String text = emailService.sendSimpleMessage(email, "reset password",
                "localhost:8080/user/resetPassword/" + token);

        return text;
    }

    @GetMapping("/resetPassword/{token}")
    public String getResetPassword(@PathVariable(name = "token") String token) {
        resetPasswordService.validToken(token);

        return "user/resetPassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/resetPassword/{token}")
    public Long postResetPassword(@PathVariable(name = "token") String token,
                                  String newPassword) {
        String email = resetPasswordService.getUserEmail(token);

        Long userId = userService.resetPassword(email, newPassword);
        return userId;
    }
}
