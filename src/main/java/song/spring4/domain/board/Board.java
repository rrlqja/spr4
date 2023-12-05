package song.spring4.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.spring4.domain.user.User;
import song.spring4.entity.BaseTimeEntity;
import song.spring4.domain.comment.Comment;
import song.spring4.entity.FileEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<FileEntity> fileEntityList = new ArrayList<>();

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer views;

    private Board(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.views = 0;
    }

    public static Board of(User user, String title, String content) {
        return new Board(user, title, content);
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViews() {
        this.views++;
    }
}
