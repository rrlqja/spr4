package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Comment;

@Getter @Setter
public class RequestCommentDto {
    private Long parentId;
    private String content;

    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }
}
