package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import song.spring4.dto.UploadFileDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    public List<UploadFileDto> upload(List<MultipartFile> multipartFileList) throws IOException {
        if (multipartFileList.isEmpty()) {
            return null;
        }
        List<UploadFileDto> uploadFileList = new ArrayList<>();
        for (MultipartFile file : multipartFileList) {
            if (!file.isEmpty()) {
                UploadFileDto uploadFile = upload(file);
                uploadFileList.add(uploadFile);
            }
        }

        return uploadFileList;
    }

    public UploadFileDto upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String saveFileName = createSaveFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(saveFileName)));

        return new UploadFileDto(originalFilename, saveFileName);
    }

    public void delete(String saveFileName) {
        File file = new File(getFullPath(saveFileName));

        if (file.exists()) {
            file.delete();
        }
    }

    public String getFullPath(String saveFileName) {
        return uploadPath + saveFileName;
    }

    private String createSaveFileName(String originalFilename) {
        String ext = getExt(originalFilename);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return originalFilename.substring(0, originalFilename.lastIndexOf(".")) + uuid + "." + ext;
    }

    private String getExt(String originalFilename) {
        int p = originalFilename.lastIndexOf(".");
        return originalFilename.substring(p + 1);
    }
}
