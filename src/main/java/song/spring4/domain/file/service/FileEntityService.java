package song.spring4.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import song.spring4.domain.file.dto.UploadFileDto;
import song.spring4.domain.board.Board;
import song.spring4.domain.file.FileEntity;
import song.spring4.exception.notfound.exceptions.FileEntityNotFoundException;
import song.spring4.exception.notfound.exceptions.BoardNotFoundException;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.file.repository.FileEntityJpaRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileEntityService {
    private final FileEntityJpaRepository fileEntityRepository;
    private final BoardJpaRepository boardRepository;
    private final FileService fileService;

    @Transactional
    public UploadFileDto createFileEntity(MultipartFile multipartFile) throws IOException {
        UploadFileDto uploadFileDto = fileService.upload(multipartFile);

        FileEntity fileEntity = FileEntity.of(uploadFileDto.getOriginalFileName(), uploadFileDto.getSavedFileName());
        fileEntityRepository.save(fileEntity);

        return uploadFileDto;
    }

    @Transactional
    public void updateFileEntity(Long boardId) {
        Board board = getBoard(boardId);
        List<String> savedFileNameList = getFileNameList(Jsoup.parse(board.getContent()));

        List<FileEntity> fileEntityList = fileEntityRepository.findAllBySavedFileNameList(savedFileNameList);
        fileEntityList.forEach(fileEntity -> fileEntity.setBoard(board));

        fileEntityRepository.saveAll(fileEntityList);
    }

    @Transactional
    public void editFileEntity(Long boardId) {
        Board board = getBoard(boardId);

        Document doc = Jsoup.parse(board.getContent());
        Elements imgs = doc.select("img");

        List<String> editImgList = getEditImgList(imgs);
        List<FileEntity> editFileEntityList = fileEntityRepository.findAllBySavedFileNameList(editImgList);
        editFileEntityList.forEach(fileEntity -> fileEntity.setBoard(board));

        board.getFileEntityList().stream()
                .filter(fileEntity -> !editFileEntityList.contains(fileEntity))
                .forEach(FileEntity::removeBoard);
    }

    @Transactional
    public UrlResource findBySavedFileName(String savedFileName) throws MalformedURLException {
        FileEntity fileEntity = fileEntityRepository.findBySavedFileName(savedFileName)
                .orElseThrow(FileEntityNotFoundException::new);

        return new UrlResource("file:" + fileService.getFullPath(fileEntity.getSavedFileName()));
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    private List<String> getFileNameList(Document content) {
        Elements imgs = content.select("img");
        return imgs.stream()
                .map(img -> {
                    String imgUrl = img.attr("src");
                    String savedFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
                    log.info("savedFileName = {}", savedFileName);
                    return savedFileName;
                }).toList();
    }

    private List<String> getEditImgList(Elements imgs) {
        return imgs.stream()
                .map(element -> element.attr("src"))
                .map(url -> url.substring(url.lastIndexOf("/") + 1))
                .toList();
    }
}
