package song.spring4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.*;
import song.spring4.dto.userdto.*;
import song.spring4.entity.User;
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

    @GetMapping
    public String getUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          Model model) {
        User findUser = userService.findUserById(userDetails.getId());
        UserDto userDto = new UserDto(findUser);

        model.addAttribute("userDto", userDto);
        return "user/user";
    }

    @GetMapping("/updateUsername")
    public String getUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @ModelAttribute UpdateUsernameDto updateUsernameDto) {
        updateUsernameDto.setUsername(userDetails.getUsername());

        return "user/updateUsername";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateUsername")
    public void postUpdateUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @Valid @ModelAttribute UpdateUsernameDto updateUsernameDto) {
        Long id = userService.updateUsername(userDetails.getId(), updateUsernameDto.getUsername());
    }

    @GetMapping("/updatePassword")
    public String getPassword(@ModelAttribute UpdatePasswordDto updatePasswordDto) {

        return "user/updatePassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updatePassword")
    public void postUpdatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @Valid @ModelAttribute UpdatePasswordDto updatePasswordDto) {
        Long id = userService.updatePassword(userDetails.getId(), updatePasswordDto.getOriginalPassword(), updatePasswordDto.getNewPassword());

    }

    @GetMapping("/updateName")
    public String getName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @ModelAttribute UpdateNameDto updateNameDto) {
        updateNameDto.setName(userDetails.getName());

        return "user/updateName";
    }

    @PostMapping("/updateName")
    public String postUpdateName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @Valid @ModelAttribute UpdateNameDto updateNameDto) {
        Long id = userService.updateName(userDetails.getId(), updateNameDto.getName());

        return "redirect:/user";
    }

    @GetMapping("/updateEmail")
    public String getEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @ModelAttribute UpdateEmailDto updateEmailDto) {
        updateEmailDto.setEmail(userDetails.getEmail());

        return "user/updateEmail";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateEmail")
    public void postUpdateEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @Valid @ModelAttribute UpdateEmailDto updateEmailDto) {
        userService.updateEmail(userDetails.getId(), updateEmailDto.getEmail());
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
    public ResponseUsername postFindUsername(@Valid @ModelAttribute FindUsernameDto findUsernameDto) {
        String username = userService.findUsername(findUsernameDto.getName(), findUsernameDto.getEmail());

        ResponseUsername responseUsername = new ResponseUsername();
        responseUsername.setUsername(username);

        return responseUsername;
    }

    @GetMapping("/findPassword")
    public String getFindPassword(@ModelAttribute FindPasswordDto findPasswordDto) {

        return "user/findPassword";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/findPassword")
    public void postFindPassword(@Valid @ModelAttribute FindPasswordDto findPasswordDto) {
        String email = userService.findPassword(findPasswordDto.getUsername(), findPasswordDto.getName(), findPasswordDto.getEmail());
        String token = resetPasswordService.createPasswordToken(email);
        EmailDto emailDto = emailService.sendSimpleMessage(email, "reset password",
                "localhost:8080/user/resetPassword/" + token);
    }

    @GetMapping("/resetPassword/{token}")
    public String getResetPassword(@PathVariable(name = "token") String token) {
        resetPasswordService.validToken(token);

        return "user/resetPassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/resetPassword/{token}")
    public void postResetPassword(@PathVariable(name = "token") String token,
                                  @Valid @ModelAttribute ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordService.getUserEmail(token);

        Long userId = userService.resetPassword(email, resetPasswordDto.getNewPassword());

        resetPasswordService.deleteToken(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/deleteUser")
    public void postDeleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               HttpServletRequest request) {
        userService.deleteUserById(userDetails.getId());

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
