package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.spring4.service.EmailService;
import song.spring4.service.EmailVerificationTokenService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailVerifyController {

    private final EmailService emailService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/verifyEmail")
    public String postVerifyEmail(@RequestParam(value = "email") String email) {
        String token = emailVerificationTokenService.createToken(email);
        String sendEmail = emailService.sendSimpleMessage(email, "verify email", "token: " + token);

        return "이메일을 성공적으로 전송했습니다";
    }

    @ResponseBody
    @GetMapping("/verifyEmail/{token}")
    public String getVerifyEmailToken(@PathVariable(name = "token") String token) {
        Long tokenId = emailVerificationTokenService.verify(token);
        emailVerificationTokenService.delete(tokenId);

        return "정상적으로 인증되었습니다.";
    }
}
