package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.spring4.dto.*;
import song.spring4.entity.FileEntity;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.service.BoardService;
import song.spring4.service.FileEntityService;
import song.spring4.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileEntityService fileEntityService;

    @GetMapping("/save")
    public String getSaveBoard(@ModelAttribute RequestBoardDto requestBoardDto) {

        return "/board/saveBoard";
    }

    @PostMapping("/save")
    public String postSaveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                RequestBoardDto requestBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.saveBoard(userDetails.getId(), requestBoardDto);

        String content = requestBoardDto.getContent();
        Document doc = Jsoup.parse(content);
        Elements img = doc.select("img");

        for (Element element : img) {
            String imgUrl = element.attr("src");

            String saveFileName = imgUrl.substring(0, img.lastIndexOf("/") + 1);
            fileEntityService.findByBoardId()
        }


        redirectAttributes.addAttribute("id", boardId);
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
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               Model model) {
        ResponseBoardDto responseBoardDto = boardService.findBoardById(id);

        validUser(userDetails.getId(), responseBoardDto.getWriterId());

        EditBoardDto editBoardDto = new EditBoardDto();
        editBoardDto.setId(responseBoardDto.getId());
        editBoardDto.setTitle(responseBoardDto.getTitle());
        editBoardDto.setContent(responseBoardDto.getContent());

        model.addAttribute("editBoardDto", editBoardDto);

        return "/board/editBoard";
    }

    @PostMapping("{id}/edit")
    public String postEditBoard(@PathVariable(name = "id") Long id,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                @ModelAttribute EditBoardDto editBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.editBoard(id, editBoardDto);
        List<FileEntity> fileEntityList = fileEntityService.findByBoardId(boardId);

        Document doc = Jsoup.parse(editBoardDto.getContent());
        Elements img = doc.select("img");

        List<String> editImgList = img.stream().map(element -> element.attr("src"))
                .map(this::extractFileNameFromUrl)
                .toList();

        redirectAttributes.addAttribute("id", boardId);

        return "redirect:/board/{id}";
    }

    @PostMapping("{id}/delete")
    public String deleteBoard(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);

        return "redirect:/";
    }

    private void validUser(Long userId, Long boardWriterId) {
        if (!userId.equals(boardWriterId)) {
            throw new IllegalRequestArgumentException("권한이 없습니다.");
        }
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(0, url.lastIndexOf("/"));
    }
}
