package song.spring4.dto.commentdto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.comment.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommentDto {
    private Long id;
    private String content;
    private Long parentId;
    private Long boardId;
    private Long writerId;
    private String writerUsername;
    private List<CommentDto> childList = new ArrayList<>();

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.boardId = comment.getBoard().getId();
        this.writerId = comment.getUser() != null ? comment.getUser().getId() : null;
        this.writerUsername = comment.getUser() != null ? comment.getUser().getUsername() : null;
        this.childList = comment.getChildList().stream().map(CommentDto::new).toList();
    }
}
