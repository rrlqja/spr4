package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.EmailDto;
import song.spring4.service.EmailService;
import song.spring4.service.EmailVerificationTokenService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailVerifyController {

    private final EmailService emailService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/verifyEmail")
    public void postVerifyEmail(@RequestParam(value = "email") String email) {
        String token = emailVerificationTokenService.createToken(email);
        EmailDto emailDto = emailService.sendSimpleMessage(email, "verify email", "token: " + token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verifyEmail/{token}")
    public void postVerifyEmailToken(@PathVariable(name = "token") String token) {
        Long tokenId = emailVerificationTokenService.verify(token);
        emailVerificationTokenService.delete(tokenId);
    }
}
