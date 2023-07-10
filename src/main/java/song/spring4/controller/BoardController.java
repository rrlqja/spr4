package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.spring4.dto.RequestBoardDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.entity.Board;
import song.spring4.service.BoardService;
import song.spring4.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String getSaveBoard(@ModelAttribute RequestBoardDto requestBoardDto) {

        return "/board/save";
    }

    @ResponseBody
    @PostMapping("/save")
    public ResponseBoardDto postSaveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                RequestBoardDto requestBoardDto) {
        Long id = boardService.saveBoard(userDetails.getId(), requestBoardDto);

        return boardService.findBoardById(id);
    }
}
