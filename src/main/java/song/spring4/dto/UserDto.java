package song.spring4.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.entity.User;

@Getter @Setter
public class UserDto {
    private String username;
    private String name;
    private String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
