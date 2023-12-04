package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.UploadFileDto;
import song.spring4.domain.board.Board;
import song.spring4.entity.FileEntity;
import song.spring4.exception.FileEntityNotFoundException;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.FileEntityJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileEntityService {
    private final FileEntityJpaRepository fileEntityRepository;
    private final BoardJpaRepository boardRepository;

    @Transactional
    public void saveFileEntity(List<UploadFileDto> uploadFileList) {
        if (uploadFileList.isEmpty()) {
            return;
        }
        List<FileEntity> fileEntityList = new ArrayList<>();

        for (UploadFileDto uploadFileDto : uploadFileList) {
            FileEntity fileEntity = new FileEntity(uploadFileDto.getUploadFileName(), uploadFileDto.getFileName());
            fileEntityList.add(fileEntity);
        }

        fileEntityRepository.saveAll(fileEntityList);
    }

    @Transactional
    public void saveFileEntity(UploadFileDto uploadFileDto) {
        FileEntity fileEntity = new FileEntity(uploadFileDto.getUploadFileName(), uploadFileDto.getFileName());

        fileEntityRepository.save(fileEntity);
    }

    @Transactional
    public List<FileEntity> findByBoardId(Long boardId) {
        return fileEntityRepository.findByBoardId(boardId);
    }

    @Transactional
    public void attachFileEntityToBoard(String saveFileName, Long boardId) {
        Board findBoard = getBoardById(boardId);

        FileEntity findFileEntity = getFileEntityBySaveFileName(saveFileName);

        findFileEntity.setBoard(findBoard);
    }

    @Transactional
    public void detachFileEntityBySaveFileName(String saveFileName) {
        FileEntity findFileEntity = getFileEntityBySaveFileName(saveFileName);

        fileEntityRepository.delete(findFileEntity);
    }

    private FileEntity getFileEntityBySaveFileName(String saveFileName) {
        Optional<FileEntity> findFileEntity = fileEntityRepository.findBySaveFileName(saveFileName);
        if (findFileEntity.isEmpty()) {
            throw new FileEntityNotFoundException("파일을 찾을 수없습니다");
        }
        return findFileEntity.get();
    }

    private Board getBoardById(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
        }
        return findBoard.get();
    }
}
