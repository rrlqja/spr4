package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.*;
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

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping
    public UserDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) {
        User findUser = userService.findUserById(userDetails.getId());
        UserDto userDto = UserMapper.toUserDto(findUser);

        model.addAttribute("userDto", userDto);
        return userDto;
    }

    @GetMapping("/updateUsername")
    public String getUpdateUsername() {

        return "user/updateUsername";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateUsername")
    public void postUpdateUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @RequestParam String newUsername) {
        Long id = userService.updateUsername(userDetails.getId(), newUsername);
    }

    @GetMapping("/updatePassword")
    public String getUpdatePassword() {

        return "user/updatePassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updatePassword")
    public void postUpdatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @RequestParam String newPassword) {
        Long id = userService.updatePassword(userDetails.getId(), newPassword);
    }

    @GetMapping("/updateName")
    public String getUpdateName() {

        return "user/updateName";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateName")
    public void postUpdateName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam String newName) {
        Long id = userService.updateName(userDetails.getId(), newName);
    }

    @GetMapping("/updateEmail")
    public String getUpdateEmail() {
        /**
         * get updateEmail -> get emailVerify -> post emailVerify -> post updateEmail
         */

        return "user/updateEmail";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateEmail")
    public void postUpdateEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestParam String newEmail) {
        userService.updateEmail(userDetails.getId(), newEmail);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/validUsername")
    public void postCheckUsername(@RequestParam String username) {
        userService.validUsername(username);
    }

    @GetMapping("/findUsername")
    public String getFindUsername(@ModelAttribute FindUsernameDto findUsernameDto) {

        return "user/findUsername";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/findUsername")
    public ResponseUsername postFindUsername(@ModelAttribute FindUsernameDto findUsernameDto) {
        ResponseUsername responseUsername = userService.findUsername(findUsernameDto);

        return responseUsername;
    }

    @GetMapping("/findPassword")
    public String getFindPassword(@ModelAttribute FindPasswordDto findPasswordDto) {

        return "user/findPassword";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/findPassword")
    public void postFindPassword(@ModelAttribute FindPasswordDto findPasswordDto) {
        String email = userService.findPassword(findPasswordDto);
        String token = resetPasswordService.createPasswordToken(email);
        EmailDto emailDto = emailService.sendSimpleMessage(email, "reset password",
                "localhost:8080/user/resetPassword/" + token);
        emailService.saveEmail(emailDto);
    }

    @GetMapping("/resetPassword/{token}")
    public String getResetPassword(@PathVariable(name = "token") String token) {
        resetPasswordService.validToken(token);

        return "user/resetPassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/resetPassword/{token}")
    public void postResetPassword(@PathVariable(name = "token") String token,
                                  String newPassword) {
        String email = resetPasswordService.getUserEmail(token);

        Long userId = userService.resetPassword(email, newPassword);
    }
}
