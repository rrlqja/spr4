package song.spring4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
