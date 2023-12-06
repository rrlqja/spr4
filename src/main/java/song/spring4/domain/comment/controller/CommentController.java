package song.spring4.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.domain.comment.dto.EditCommentDto;
import song.spring4.domain.comment.dto.RequestCommentDto;
import song.spring4.domain.comment.Comment;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.security.pricipal.UserPrincipal;
import song.spring4.domain.comment.service.CommentService;

@Slf4j
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserPrincipal userPrincipal,
                       @ModelAttribute RequestCommentDto requestCommentDto,
                       RedirectAttributes redirectAttributes) {
        Long bardId = commentService.saveComment(userPrincipal.getId(), requestCommentDto);

        redirectAttributes.addAttribute("boardId", bardId);

        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{commentId}/edit")
    public String getEditComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @PathVariable(name = "commentId") Long commentId,
                                 Model model) {
        Comment comment = commentService.findCommentById(commentId);

        validUser(userPrincipal.getId(), comment.getUser().getId());

        EditCommentDto editCommentDto = new EditCommentDto(comment);

        model.addAttribute("editCommentDto", editCommentDto);

        return "comment/editComment";
    }

    @PostMapping("/{commentId}/edit")
    public String postEditComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                  @PathVariable(name = "commentId") Long commentId,
                                  @ModelAttribute EditCommentDto editCommentDto,
                                  RedirectAttributes redirectAttributes) {
        Long boardId = commentService.editComment(userPrincipal.getId(), editCommentDto);

        redirectAttributes.addAttribute("id", boardId);
        return "redirect:/board/{id}";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{commentId}/delete")
    public void delete(@PathVariable(name = "commentId") Long id) {
        Long deleteId = commentService.deleteComment(id);
    }

    private void validUser(Long userId, Long boardWriterId) {
        if (!userId.equals(boardWriterId)) {
            throw new IllegalRequestArgumentException("권한이 없습니다.");
        }
    }
}
