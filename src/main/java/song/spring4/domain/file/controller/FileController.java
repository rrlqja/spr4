package song.spring4.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import song.spring4.domain.file.dto.UploadFileDto;
import song.spring4.domain.file.service.FileEntityService;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileEntityService fileEntityService;

    @ResponseBody
    @PostMapping("/uploadFile")
    public UploadFileDto postUpload(@RequestParam MultipartFile upload) throws IOException {
        UploadFileDto uploadFileDto = fileEntityService.createFileEntity(upload);

        return uploadFileDto;
    }

    @ResponseBody
    @GetMapping("/downloadFile/{savedFileName}")
    public ResponseEntity<Resource> getDownload(@PathVariable(value = "savedFileName") String savedFileName) throws MalformedURLException {
        UrlResource resource = fileEntityService.findBySavedFileName(savedFileName);
        String contentType = getContentType(savedFileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }
}
