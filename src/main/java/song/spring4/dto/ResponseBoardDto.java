package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ResponseBoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createTime;

    private List<CommentDto> commentList = new ArrayList<>();

    public ResponseBoardDto(Board board) {
        this.id = board.getId();
        this.writer = board.getWriter().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createTime = board.getCreateDate();
        this.commentList = board.getCommentList().stream().map(CommentDto::new).toList();
    }
}
