package song.spring4.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.spring4.domain.comment.entity.Comment;

@Getter @Setter
@NoArgsConstructor
public class EditCommentDto {
    private Long id;
    private String content;

    public EditCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
