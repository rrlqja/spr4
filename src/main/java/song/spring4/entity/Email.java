package song.spring4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Email {

    @Id @GeneratedValue
    private Long id;

    private String from;
    private String[] to;
    private String subject;
    private String content;
    private Date sendDate;
}
