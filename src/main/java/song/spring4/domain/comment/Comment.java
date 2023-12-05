package song.spring4.domain.comment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.spring4.domain.board.Board;
import song.spring4.domain.user.User;
import song.spring4.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> childList = new ArrayList<>();

    private String content;

    private Comment(User user, Board board, Comment parent, String content) {
        this.user = user;
        this.board = board;
        this.parent = parent;
        this.content = content;
    }

    public static Comment of(User user, Board board, Comment parent, String content) {
        return new Comment(user, board, parent, content);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.user = null;
        this.content = "삭제된 댓글입니다.";
    }
}
