package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Board;

@Getter @Setter
public class BoardListDto {
    private Long id;
    private String title;
    private String writer;

    public BoardListDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter().getUsername();
    }
}
