package song.spring4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.domain.comment.dto.BoardListDto;
import song.spring4.dto.LoginDto;
import song.spring4.dto.SignupDto;
import song.spring4.service.BoardService;
import song.spring4.service.UserRoleService;
import song.spring4.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final BoardService boardService;
    private final UserRoleService userRoleService;

    private static final String SEARCH_WRITER = "writer";
    private static final String SEARCH_TITLE = "title";
    private static final String SEARCH_CONTENT = "content";

    @GetMapping("/")
    public String getHome(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "searchType", required = false) String searchType,
                          @RequestParam(value = "searchValue", required = false) String searchValue,
                          Model model) {
        Page<BoardListDto> boardPage = null;
        if (SEARCH_WRITER.equals(searchType)) {
            boardPage = boardService.findBoardListByUsername(searchValue, pageable);
        } else if (SEARCH_TITLE.equals(searchType)) {
            boardPage = boardService.findBoardListByTitle(searchValue, pageable);
        } else if (SEARCH_CONTENT.equals(searchType)) {
            boardPage = boardService.findBoardByContent(searchValue, pageable);
        } else {
            boardPage = boardService.findBoardList(pageable);
        }

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

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
        userRoleService.grantRole(id, Role.ROLE_USER.name());
    }


    @GetMapping("/login")
    private String getLogin(@ModelAttribute LoginDto loginDto) {

        return "login/login";
    }
}
