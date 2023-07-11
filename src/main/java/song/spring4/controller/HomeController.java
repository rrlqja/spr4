package song.spring4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.spring4.dto.BoardListDto;
import song.spring4.dto.LoginDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.dto.SignupDto;
import song.spring4.service.BoardService;
import song.spring4.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/")
    public String getHome(@PageableDefault(size = 10, page = 0) Pageable pageable,
                          Model model) {
        Page<BoardListDto> boardPage = boardService.findBoardList(pageable);

        model.addAttribute("boardPage", boardPage);

        return "home";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupDto signupDto) {

        return "signup";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void postSignup(@Valid @ModelAttribute SignupDto signupDto) {
        Long id = userService.join(signupDto);
    }


    @GetMapping("/login")
    private String getLogin(@ModelAttribute LoginDto loginDto) {

        return "login";
    }
}
