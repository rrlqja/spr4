package song.spring4.domain.oauth2.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.spring4.domain.common.entity.BaseTimeEntity;
import song.spring4.domain.user.entity.User;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Sns extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String snsId;
    private String snsName;
    private String snsEmail;

    @Builder
    public Sns(String snsId, String snsName, String snsEmail, User user) {
        this.snsId = snsId;
        this.snsName = snsName;
        this.snsEmail = snsEmail;
        this.user = user;

//        user.getSnsList().add(this);
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("snsId", snsId);
        attributes.put("snsName", snsName);
        attributes.put("snsEmail", snsEmail);

        return Map.copyOf(attributes);
    }
}
