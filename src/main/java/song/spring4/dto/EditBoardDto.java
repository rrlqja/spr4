package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditBoardDto {
    private Long id;
    private String title;
    private String content;
}
