package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.domain.board.dto.RequestBoardDto;
import song.spring4.domain.board.dto.ResponseBoardDto;
import song.spring4.dto.boarddto.EditBoardDto;
import song.spring4.dto.commentdto.SaveCommentDto;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.security.pricipal.UserPrincipal;
import song.spring4.service.BoardService;
import song.spring4.service.FileEntityService;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FileEntityService fileEntityService;

    @GetMapping("/save")
    public String getSaveBoard(@ModelAttribute RequestBoardDto requestBoardDto) {

        return "board/saveBoard";
    }

    @PostMapping("/save")
    public String postSaveBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @ModelAttribute RequestBoardDto requestBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.saveBoard(userPrincipal.getId(), requestBoardDto);
        fileEntityService.updateFileEntity(boardId);

        redirectAttributes.addAttribute("id", boardId);
        return "redirect:/board/{id}";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable(name = "id") Long id,
                           @ModelAttribute SaveCommentDto saveCommentDto,
                           Model model) {
        ResponseBoardDto boardDto = boardService.findBoardById(id);
        model.addAttribute("board", boardDto);

        return "board/board";
    }

    @GetMapping("/{id}/edit")
    public String getEditBoard(@PathVariable(name = "id") Long id,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               Model model) {
        ResponseBoardDto boardDto = boardService.findBoardById(id);
        validUser(userPrincipal.getId(), boardDto.getUserId());

        EditBoardDto editBoardDto = new EditBoardDto(boardDto.getId(), boardDto.getTitle(), boardDto.getContent());
        model.addAttribute("editBoardDto", editBoardDto);

        return "board/editBoard";
    }

    @PostMapping("/{id}/edit")
    public String postEditBoard(@PathVariable(name = "id") Long id,
                                @AuthenticationPrincipal UserPrincipal userPrincipal,
                                @ModelAttribute EditBoardDto editBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.editBoard(id, editBoardDto);
        fileEntityService.editFileEntity(boardId);

        redirectAttributes.addAttribute("id", boardId);

        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);

        return "redirect:/";
    }

    private void validUser(Long userId, Long boardWriterId) {
        if (!userId.equals(boardWriterId)) {
            throw new IllegalRequestArgumentException("권한이 없습니다.");
        }
    }
}
