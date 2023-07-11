package song.spring4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationToken {
    @Id @GeneratedValue
    private Long id;

    private String token;
    private String email;
    private boolean isVerified;
    private LocalDateTime createTime;
    private LocalDateTime expiryTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
        this.expiryTime = createTime.plusMinutes(5L);
    }
}
