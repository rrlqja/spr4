package song.spring4.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import song.spring4.domain.user.entity.User;

@Getter @Setter
public class ResponseUserDto {
    private String username;
    private String name;
    private String email;

    public ResponseUserDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
