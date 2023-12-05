package song.spring4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.userdto.FindPasswordDto;
import song.spring4.dto.userdto.FindUsernameDto;
import song.spring4.dto.userdto.ResetPasswordDto;
import song.spring4.dto.userdto.ResponseUsername;
import song.spring4.service.AccountService;

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/findUsername")
    public String getFindUsername(@ModelAttribute FindUsernameDto findUsernameDto) {

        return "login/findUsername";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/findUsername")
    public ResponseUsername postFindUsername(@Valid @ModelAttribute FindUsernameDto findUsernameDto) {
        ResponseUsername responseUsername = accountService.findUsername(findUsernameDto.getName(), findUsernameDto.getEmail());

        return responseUsername;
    }

    @GetMapping("/findPassword")
    public String getFindPassword(@ModelAttribute FindPasswordDto findPasswordDto) {

        return "login/findPassword";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/findPassword")
    public void postFindPassword(@Valid @ModelAttribute FindPasswordDto findPasswordDto) {
        accountService.createResetPasswordToken(findPasswordDto.getUsername(), findPasswordDto.getName(), findPasswordDto.getEmail());
    }

    @GetMapping("/resetPassword/{token}")
    public String getResetPassword(@PathVariable(name = "token") String token) {
        accountService.validateRestPasswordToken(token);

        return "login/resetPassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/resetPassword/{token}")
    public void postResetPassword(@PathVariable(name = "token") String token,
                                  @Valid @ModelAttribute ResetPasswordDto resetPasswordDto) {
        accountService.resetPassword(token, resetPasswordDto.getNewPassword());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/verifyEmail")
    public void postVerifyEmail(@RequestParam(value = "email") String email) {
        accountService.createEmailVerificationToken(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verifyEmail/{token}")
    public void postVerifyEmailToken(@PathVariable(name = "token") String token) {
        accountService.verify(token);
    }
}
