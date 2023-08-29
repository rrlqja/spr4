package song.spring4.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> fileEntityList = new ArrayList<>();

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private Long views;

    @Builder
    public Board(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0L;
    }

    public void setWriter(User writer) {
        this.writer = writer;
        writer.getBoardList().add(this);
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViews() {
        this.views++;
    }

}
