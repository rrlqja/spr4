package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.Board;

@Getter @Setter
public class RequestBoardDto {
    private String title;
    private String content;

    public Board toEntity() {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setViews(0L);
        return board;
    }
}
