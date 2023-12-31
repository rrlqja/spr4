package song.spring4.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.comment.dto.CommentDto;
import song.spring4.domain.board.entity.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardDto {
    private Long id;
    private Long writerId;
    private String writerUsername;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createDate;
    private List<CommentDto> commentList = new ArrayList<>();

    public BoardDto(Board board) {
        this.id = board.getId();
        this.writerId = board.getUser().getId();
        this.writerUsername = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createDate = board.getCreateDate();
        this.commentList = board.getCommentList().stream().map(CommentDto::new).toList();
    }
}
