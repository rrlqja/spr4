package song.spring4.dto.boarddto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.board.Board;

@Getter @Setter
public class SaveBoardDto {
    private String title;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
