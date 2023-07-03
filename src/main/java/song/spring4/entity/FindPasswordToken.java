package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class FindPasswordToken {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String token;

    @Column(updatable = false)
    private LocalDateTime createTime;

    private LocalDateTime expiryTime;

    @PrePersist
    private void prePersist() {
        createTime = LocalDateTime.now();
        expiryTime = createTime.plusHours(3);
    }
}
