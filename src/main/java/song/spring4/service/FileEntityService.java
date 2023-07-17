package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.UploadFileDto;
import song.spring4.entity.FileEntity;
import song.spring4.exception.FileEntityNotFoundException;
import song.spring4.repository.FileEntityJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileEntityService {
    private final FileEntityJpaRepository fileEntityRepository;

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
    public String findBySaveFileName(String saveFileName) {
        Optional<FileEntity> findFile = fileEntityRepository.findBySaveFileName(saveFileName);
        if (findFile.isEmpty()) {
            throw new FileEntityNotFoundException("파일을 찾을 수 없습니다");
        }
        FileEntity fileEntity = findFile.get();
        return fileEntity.getSaveFileName();
    }
}
