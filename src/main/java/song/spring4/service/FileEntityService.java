package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.UploadFileDto;
import song.spring4.entity.Board;
import song.spring4.entity.FileEntity;
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
    public void saveFileEntity(List<UploadFileDto> uploadFileList, Long boardId) {
        if (uploadFileList.isEmpty()) {
            return;
        }
        Board findBoard = getBoardById(boardId);
        List<FileEntity> fileEntityList = new ArrayList<>();

        for (UploadFileDto uploadFileDto : uploadFileList) {
            FileEntity fileEntity = new FileEntity(uploadFileDto.getUploadFileName(), uploadFileDto.getSaveFileName());
            fileEntity.setBoard(findBoard);
            fileEntityList.add(fileEntity);
        }

        fileEntityRepository.saveAll(fileEntityList);
    }

    private Board getBoardById(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
        }
        return findBoard.get();
    }
}
