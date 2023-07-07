package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Email {

    @Id @GeneratedValue
    private Long id;

    private String fromEmail;

    @ElementCollection
    @CollectionTable(name = "toEmail", joinColumns = @JoinColumn(name = "email_id"))
    private List<String> toEmail = new ArrayList<>();

    private String subject;
    private String content;
}