package song.spring4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationToken {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime expiryTime;

    private EmailVerificationToken(String email, String token) {
        this.email = email;
        this.token = token;
        createTime = LocalDateTime.now();
        expiryTime = createTime.plusMinutes(5L);
    }

    public static EmailVerificationToken of(String email, String token) {
        return new EmailVerificationToken(email, token);
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
