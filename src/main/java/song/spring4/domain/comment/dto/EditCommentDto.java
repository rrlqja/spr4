package song.spring4.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.comment.Comment;

@Getter @Setter
public class EditCommentDto {
    private Long id;
    private String content;

    public EditCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
