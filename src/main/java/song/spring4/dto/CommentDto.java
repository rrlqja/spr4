package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Comment;

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
        this.writerUsername = comment.getWriter().getUsername();
        this.childList = comment.getChildList().stream().map(CommentDto::new).toList();
    }
}
