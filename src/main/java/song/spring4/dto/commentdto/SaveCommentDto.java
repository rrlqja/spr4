package song.spring4.dto.commentdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveCommentDto {
    private Long parentId;
    private Long boardId;
    private String content;
}
