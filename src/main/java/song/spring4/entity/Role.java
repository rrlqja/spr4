package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.spring4.entity.role.RoleName;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }
}
