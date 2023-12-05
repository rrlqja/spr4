package song.spring4.dto.boarddto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditBoardDto {
    private Long id;
    private String title;
    private String content;

    public EditBoardDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
