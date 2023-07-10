package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Board extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String title;
    private String content;

    public void setWriter(User writer) {
        this.writer = writer;
        writer.getBoardList().add(this);
    }

}
