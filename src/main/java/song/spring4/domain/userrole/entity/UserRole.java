package song.spring4.domain.userrole.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.userrole.consts.Role;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    private UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        user.addUserRole(this);
    }

    public static UserRole of(User user, Role role) {
        return new UserRole(user, role);
    }
}
