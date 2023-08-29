package song.spring4.dto.boarddto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Board;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardListDto {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createDate;

    public BoardListDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter().getUsername();
        this.createDate = board.getCreateDate();
    }
}
