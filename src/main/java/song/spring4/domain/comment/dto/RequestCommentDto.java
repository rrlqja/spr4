package song.spring4.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestCommentDto {
    private Long boardId;
    private Long parentId;
    private String content;
}
