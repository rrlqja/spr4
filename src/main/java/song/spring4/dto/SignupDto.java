package song.spring4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.User;

@Getter @Setter
public class SignupDto {

    @NotBlank(message = "username is required")
    @Size(min=10, message = "username must be at least 10 characters")
    private String username;
    private String password;
    private String name;
    private String email;

    public User toEntity() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }
}
