package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserRole {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public UserRole(Role role) {
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
