package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import song.spring4.dto.UploadFileDto;
import song.spring4.service.FileEntityService;
import song.spring4.service.UploadService;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final UploadService uploadService;
    private final FileEntityService fileEntityService;

    @ResponseBody
    @PostMapping("/uploadFile")
    public UploadFileDto postUpload(@RequestParam MultipartFile upload) throws IOException {
        UploadFileDto uploadFileDto = uploadService.upload(upload);
        fileEntityService.saveFileEntity(uploadFileDto);

        return uploadFileDto;
    }

    @ResponseBody
    @GetMapping("/downloadFile/{fileName}")
    public Resource getDownload(@PathVariable(value = "fileName") String saveFileName) throws MalformedURLException {
        String fileName = fileEntityService.findBySaveFileName(saveFileName);
        return new UrlResource("file:" + uploadService.getFullPath(fileName));
    }
}
