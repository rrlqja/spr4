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
import song.spring4.dto.boarddto.BoardDto;
import song.spring4.dto.boarddto.SaveBoardDto;
import song.spring4.dto.boarddto.EditBoardDto;
import song.spring4.dto.commentdto.SaveCommentDto;
import song.spring4.entity.FileEntity;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.service.BoardService;
import song.spring4.service.FileEntityService;
import song.spring4.service.FileService;
import song.spring4.security.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileEntityService fileEntityService;
    private final FileService fileService;

    @GetMapping("/save")
    public String getSaveBoard(@ModelAttribute SaveBoardDto saveBoardDto) {

        return "board/saveBoard";
    }

    @PostMapping("/save")
    public String postSaveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @ModelAttribute SaveBoardDto saveBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.saveBoard(userDetails.getId(), saveBoardDto);

        BoardDto boardDto = boardService.findBoardById(boardId);
        String content = boardDto.getContent();
        Document doc = Jsoup.parse(content);
        Elements img = doc.select("img");

        for (Element element : img) {
            String imgUrl = element.attr("src");

            String saveFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
            System.out.println("saveFileName = " + saveFileName);

            fileEntityService.attachFileEntityToBoard(saveFileName, boardId);
        }

        redirectAttributes.addAttribute("id", boardId);
        return "redirect:/board/{id}";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable(name = "id") Long id,
                           @ModelAttribute SaveCommentDto saveCommentDto,
                           Model model) {
        BoardDto boardDto = boardService.findBoardById(id);
        model.addAttribute("boardDto", boardDto);

        return "board/board";
    }

    @GetMapping("/{id}/edit")
    public String getEditBoard(@PathVariable(name = "id") Long id,
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               @ModelAttribute EditBoardDto editBoardDto) {
        BoardDto boardDto = boardService.findBoardById(id);

        validUser(userDetails.getId(), boardDto.getWriterId());

        editBoardDto.setId(boardDto.getId());
        editBoardDto.setTitle(boardDto.getTitle());
        editBoardDto.setContent(boardDto.getContent());

        return "board/editBoard";
    }

    @PostMapping("/{id}/edit")
    public String postEditBoard(@PathVariable(name = "id") Long id,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                @ModelAttribute EditBoardDto editBoardDto,
                                RedirectAttributes redirectAttributes) {
        Long boardId = boardService.editBoard(id, editBoardDto);

        Document doc = Jsoup.parse(editBoardDto.getContent());
        Elements img = doc.select("img");

//        get Edit ImgList
        List<String> editImgList = img.stream()
                .map(element -> element.attr("src"))
                .map(this::extractFileNameFromUrl)
                .toList();

//        get Board ImgList
        List<FileEntity> fileEntityList = fileEntityService.findByBoardId(boardId);
        List<String> boardFileList = fileEntityList.stream()
                .map(FileEntity::getSaveFileName)
                .toList();

//        get new ImgList from EditBoardDto
        List<String> addFileNameList = editImgList.stream()
                .filter(fileName -> !boardFileList.contains(fileName))
                .toList();

//        get removeImgList from Board
        List<String> removeFileList = boardFileList.stream()
                .filter(fileName -> !editImgList.contains(fileName))
                .toList();

        for (String fileName : addFileNameList) {
            fileEntityService.attachFileEntityToBoard(fileName, boardId);
        }

        for (String fileName : removeFileList) {
            fileEntityService.detachFileEntityBySaveFileName(fileName);
            fileService.delete(fileName);
        }

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

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
