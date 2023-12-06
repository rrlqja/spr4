package song.spring4.domain.account;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordToken {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String token;
    @Column(updatable = false)
    private LocalDateTime createTime;
    private LocalDateTime expiryTime;

    private ResetPasswordToken(String email, String token) {
        this.email = email;
        this.token = token;
        createTime = LocalDateTime.now();
        expiryTime = createTime.plusHours(3);
    }

    public static ResetPasswordToken of(String email, String token) {
        return new ResetPasswordToken(email, token);
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
