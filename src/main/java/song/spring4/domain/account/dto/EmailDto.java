package song.spring4.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailDto {
    private String fromEmail;
    private String[] toEmail;
    private String subject;
    private String content;
}
