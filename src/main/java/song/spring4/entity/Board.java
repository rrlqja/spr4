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
    private String content;
    private Long views;

    public void setWriter(User writer) {
        this.writer = writer;
        writer.getBoardList().add(this);
    }

}
