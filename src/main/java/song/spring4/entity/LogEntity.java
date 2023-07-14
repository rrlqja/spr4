package song.spring4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class LogEntity {
    @Id @GeneratedValue
    private Long id;

    private String className;
    private String methodName;
    private LocalDateTime requestTime;
    private Long resultTimeMs;

}
