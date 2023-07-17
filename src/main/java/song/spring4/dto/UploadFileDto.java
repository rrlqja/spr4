package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadFileDto {
    private String uploadFileName;
    private String saveFileName;

    public UploadFileDto(String originalFilename, String saveFileName) {
        this.uploadFileName = originalFilename;
        this.saveFileName = saveFileName;
    }
}
