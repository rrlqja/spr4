package song.spring4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> childList = new ArrayList<>();

    private String content;

    public void setBoard(Board board) {
        this.board = board;

        board.getCommentList().add(this);
    }

    public void setParent(Comment parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getChildList().add(this);
        }
    }
}
