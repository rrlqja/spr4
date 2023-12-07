package song.spring4.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.spring4.domain.common.entity.BaseTimeEntity;
import song.spring4.domain.userrole.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roleList = new ArrayList<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Sns> snsList = new ArrayList<>();

    private String username;
    private String password;
    private String name;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        unexpireAccount();
        unlockAccount();
        unexpireCredentials();
        enableAccount();
    }

    public static User of(String username, String password, String name, String email) {
        return new User(username, password, name, email);
    }

    public void addUserRole(UserRole userRole) {
        this.roleList.add(userRole);
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void expireAccount() {
        this.isAccountNonExpired = false;
    }
    public void unexpireAccount() {
        this.isAccountNonExpired = true;
    }

    public void lockAccount() {
        this.isAccountNonLocked = false;
    }

    public void unlockAccount() {
        this.isAccountNonLocked = true;
    }

    public void expireCredentials() {
        this.isCredentialsNonExpired = false;
    }

    public void unexpireCredentials() {
        this.isCredentialsNonExpired = true;
    }

    public void disableAccount() {
        this.isEnabled = false;
    }

    public void enableAccount() {
        this.isEnabled = true;
    }
}
