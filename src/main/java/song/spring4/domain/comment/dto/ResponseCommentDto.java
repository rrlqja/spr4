package song.spring4.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.comment.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ResponseCommentDto {
    private Long id;
    private String content;
    private Long parentId;
    private Long userId;
    private String username;
    private List<ResponseCommentDto> childList = new ArrayList<>();

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.userId = comment.getUser() != null ? comment.getUser().getId() : null;
        this.username = comment.getUser() != null ? comment.getUser().getUsername() : null;
        this.childList = comment.getChildList().stream().map(ResponseCommentDto::new).toList();
    }
}
