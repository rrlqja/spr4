package song.spring4.domain.file.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.file.dto.UploadFileDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class FileServiceTest {
    @Autowired
    FileService fileService;
    @Value("${upload.path}")
    String path;

    @DisplayName("파일 업로드")
    @Test
    void uploadFile() throws IOException {
        String content = "mock";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "mock.txt",
                "text/plain",
                content.getBytes(StandardCharsets.UTF_8)
        );

        assertDoesNotThrow(() -> fileService.upload(mockMultipartFile));
    }

    @DisplayName("파일 삭제")
    @Test
    void deleteFile() throws Exception {
        String content = "mock";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "mock.txt",
                "text/plain",
                content.getBytes(StandardCharsets.UTF_8)
        );
        UploadFileDto uploadFileDto = fileService.upload(mockMultipartFile);

        assertDoesNotThrow(() -> fileService.delete(uploadFileDto.getSavedFileName()));
    }
}