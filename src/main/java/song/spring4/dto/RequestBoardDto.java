package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import song.spring4.entity.Board;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RequestBoardDto {
    private String title;
    private String content;
    private List<MultipartFile> files = new ArrayList<>();

    public Board toEntity() {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setViews(0L);
        return board;
    }
}
