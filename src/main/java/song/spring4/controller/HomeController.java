package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import song.spring4.dto.LoginDto;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/login")
    private String getLogin(Model model) {

        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }
}
