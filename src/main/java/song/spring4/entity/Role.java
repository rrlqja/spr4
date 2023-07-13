package song.spring4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.role.RoleName;

@Entity
@Getter @Setter
public class Role {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
