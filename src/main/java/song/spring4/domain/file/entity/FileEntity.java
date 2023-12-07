package song.spring4.domain.file.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.spring4.domain.board.entity.Board;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String originalFileName;
    private String savedFileName;

    private FileEntity(String originalFileName, String savedFileName) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public static FileEntity of(String uploadFileName, String saveFileName) {
        return new FileEntity(uploadFileName, saveFileName);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void removeBoard() {
        this.board = null;
    }
}
