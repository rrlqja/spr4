package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Board;

import java.time.LocalDateTime;

@Getter @Setter
public class ResponseBoardDto {
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createTime;

    public ResponseBoardDto(Board board) {
        this.writer = board.getWriter().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createTime = board.getCreateDate();
    }
}
