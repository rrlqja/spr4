package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadFileDto {
    private String originalFileName;
    private Long uploaded;
    private String savedFileName;
    private String url;

    public UploadFileDto(String originalFilename, String savedFileName) {
        this.uploaded = 1L;
        this.originalFileName = originalFilename;
        this.savedFileName = savedFileName;
        this.url = "/file/downloadFile/" + savedFileName;
    }
}
