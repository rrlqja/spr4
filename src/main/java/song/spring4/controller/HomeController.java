package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.spring4.dto.LoginDto;
import song.spring4.dto.SignupDto;
import song.spring4.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String getHome() {

        return "home";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupDto signupDto) {

        return "signup";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void postSignup(@ModelAttribute SignupDto signupDto) {
        Long id = userService.join(signupDto);
    }


    @GetMapping("/login")
    private String getLogin(@ModelAttribute LoginDto loginDto) {

        return "login";
    }
}
