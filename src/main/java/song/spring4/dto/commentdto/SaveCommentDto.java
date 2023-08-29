package song.spring4.dto.commentdto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Comment;

@Getter @Setter
public class SaveCommentDto {
    private Long parentId;
    private Long boardId;
    private String content;

    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }
}
