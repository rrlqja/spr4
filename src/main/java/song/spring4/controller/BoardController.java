package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.dto.RequestBoardDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.dto.UpdateBoardDto;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public Long postSaveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                RequestBoardDto requestBoardDto) {
        Long id = boardService.saveBoard(userDetails.getId(), requestBoardDto);

        return id;
    }

    @ResponseBody
    @GetMapping("{id}")
    public ResponseBoardDto getBoard(@PathVariable(name = "id") Long id) {
        ResponseBoardDto responseBoardDto = boardService.findBoardById(id);

        return responseBoardDto;
    }

    @GetMapping("{id}/update")
    public String getUpdateBoard(@PathVariable(name = "id") Long id,
                                 @ModelAttribute UpdateBoardDto updateBoardDto) {

        return "board/updateBoard";
    }

    @PostMapping("{id}/update")
    public String postUpdateBoard(@PathVariable(name = "id") Long id,
                                  @ModelAttribute UpdateBoardDto updateBoardDto,
                                  RedirectAttributes redirectAttributes) {
        Long updateId = boardService.updateBoard(id, updateBoardDto);

        redirectAttributes.addAttribute("id", updateId);

        return "redirect:/board/{id}";
    }

    @PostMapping("{id}/delete")
    public String deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable(name = "id") Long id) {
        boardService.deleteBoard(userDetails.getId(), id);

        return "redirect:/";
    }
}
