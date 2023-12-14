package song.spring4.domain.file.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.file.repository.FileEntityJpaRepository;


@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class FileEntityServiceTest {
    @Autowired
    FileEntityService fileEntityService;
    @Autowired
    FileEntityJpaRepository fileEntityRepository;
    @Autowired
    BoardJpaRepository boardRepository;

    @Test
    @Transactional
    void save1() {
//        UploadFileDto uploadFileDto = new UploadFileDto("a", "b");
//        fileEntityService.saveFileEntity(uploadFileDto);
//
//        Optional<FileEntity> findEntity = fileEntityRepository.findBySavedFileName(uploadFileDto.getSavedFileName());
//        assertThat(findEntity.isPresent()).isTrue();
    }

    @Test
    @Transactional
    void save2() {
//        UploadFileDto uploadFileDto1 = new UploadFileDto("o1", "s1");
//        UploadFileDto uploadFileDto2 = new UploadFileDto("o2", "s2");
//        UploadFileDto uploadFileDto3 = new UploadFileDto("o3", "s3");
//        UploadFileDto uploadFileDto4 = new UploadFileDto("o4", "s4");
//
//        List<UploadFileDto> uploadFileList = new ArrayList<>();
//        uploadFileList.add(uploadFileDto1);
//        uploadFileList.add(uploadFileDto2);
//        uploadFileList.add(uploadFileDto3);
//        uploadFileList.add(uploadFileDto4);
//
//        fileEntityService.saveFileEntity(uploadFileList);
//
//        List<FileEntity> entityList = fileEntityRepository.findAll();
//        assertThat(entityList.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    void t1() {
//        Board board = Board.builder()
//                .title("noFile")
//                .build();
//        Board saveBoard = boardRepository.save(board);

//        FileEntity fileEntity = new FileEntity();
//        fileEntity.setSaveFileName("noBoard");
//        FileEntity saveFileEntity = fileEntityRepository.save(fileEntity);
//
//        fileEntityService.attachFileEntityToBoard(saveFileEntity.getSaveFileName(), saveBoard.getId());
//
//        FileEntity resultEntity = fileEntityRepository.findBySaveFileName(fileEntity.getSaveFileName()).get();
//
//        assertThat(resultEntity.getBoard().getId()).isEqualTo(saveBoard.getId());
    }
}