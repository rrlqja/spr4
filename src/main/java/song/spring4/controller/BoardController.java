package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.dto.RequestBoardDto;
import song.spring4.dto.RequestCommentDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.dto.EditBoardDto;
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

        return "/board/saveBoard";
    }

    @PostMapping("/save")
    public String postSaveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                RequestBoardDto requestBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long id = boardService.saveBoard(userDetails.getId(), requestBoardDto);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/board/{id}";
    }

    @GetMapping("{id}")
    public String getBoard(@PathVariable(name = "id") Long id,
                           Model model) {
        ResponseBoardDto responseBoardDto = boardService.findBoardById(id);

        RequestCommentDto requestCommentDto = new RequestCommentDto();

        model.addAttribute("responseBoardDto", responseBoardDto);
        model.addAttribute("requestCommentDto", requestCommentDto);

        return "/board/board";
    }

    @GetMapping("{id}/edit")
    public String getEditBoard(@PathVariable(name = "id") Long id,
                               Model model) {
        ResponseBoardDto responseBoardDto = boardService.findBoardById(id);

        model.addAttribute("editBoardDto", responseBoardDto);

        return "/board/editBoard";
    }

    @PostMapping("{id}/edit")
    public String postEditBoard(@PathVariable(name = "id") Long id,
                                @ModelAttribute EditBoardDto editBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.editBoard(id, editBoardDto);

        redirectAttributes.addAttribute("id", boardId);

        return "redirect:/board/{id}";
    }

    @PostMapping("{id}/delete")
    public String deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable(name = "id") Long id) {
        boardService.deleteBoard(userDetails.getId(), id);

        return "redirect:/";
    }
}
