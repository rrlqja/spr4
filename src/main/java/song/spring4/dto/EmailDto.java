package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmailDto {
    private String from;
    private String[] to;
    private String subject;
    private String content;
    private Date sendDate;
}
