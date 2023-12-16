package song.spring4.domain.file.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.file.dto.UploadFileDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class FileEntityServiceTest {
    @Autowired
    FileEntityService fileEntityService;

    @Test
    void saveFileENtity() throws IOException {
        String content = "mock";
        String originalFilename = "mock.txt";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                originalFilename,
                "text/plain",
                content.getBytes(StandardCharsets.UTF_8)
        );

        UploadFileDto uploadFileDto = fileEntityService.createFileEntity(mockMultipartFile);

        assertThat(uploadFileDto.getOriginalFileName())
                .isEqualTo(originalFilename);
    }
}