package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.dto.EditCommentDto;
import song.spring4.dto.RequestCommentDto;
import song.spring4.entity.Comment;
import song.spring4.service.CommentService;
import song.spring4.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @ModelAttribute RequestCommentDto requestCommentDto,
                       RedirectAttributes redirectAttributes) {
        Long saveId = commentService.saveComment(userDetails.getId(), requestCommentDto);

        redirectAttributes.addAttribute("id", requestCommentDto.getBoardId());

        return "redirect:/board/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getEditComment(@PathVariable(name = "id") Long id,
                                 Model model) {
        Comment findComment = commentService.findCommentById(id);

        EditCommentDto editCommentDto = new EditCommentDto();
        editCommentDto.setId(findComment.getId());
        editCommentDto.setContent(findComment.getContent());

        model.addAttribute("editCommentDto", editCommentDto);

        return "comment/editComment";
    }

    @PostMapping("/{id}/edit")
    public String postEditComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @PathVariable(name = "id") Long id,
                                  @ModelAttribute EditCommentDto editCommentDto,
                                  RedirectAttributes redirectAttributes) {
        log.info("userId = {}, commentId = {}", userDetails.getId(), editCommentDto.getId());
        Long boardId = commentService.editComment(userDetails.getId(), editCommentDto);

        redirectAttributes.addAttribute("id", boardId);
        return "redirect:/board/{id}";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/delete")
    public void delete(@PathVariable(name = "id") Long id) {
        Long deleteId = commentService.deleteComment(id);
    }
}
