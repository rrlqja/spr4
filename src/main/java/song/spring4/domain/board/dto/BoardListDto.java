package song.spring4.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.board.entity.Board;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardListDto {
    private Long id;
    private String title;
    private String username;
    private LocalDateTime createDate;

    public BoardListDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUser().getUsername();
        this.createDate = board.getCreateDate();
    }
}
