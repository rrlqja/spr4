package song.spring4.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.comment.dto.ResponseCommentDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ResponseBoardDto {
    private Long id;
    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createDate;

    private Long userId;
    private String username;

    private List<ResponseCommentDto> commentList = new ArrayList<>();

    public ResponseBoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createDate = board.getCreateDate();

        this.userId = board.getUser().getId();
        this.username = board.getUser().getUsername();

        this.commentList = board.getCommentList().stream().map(ResponseCommentDto::new).toList();
    }
}
