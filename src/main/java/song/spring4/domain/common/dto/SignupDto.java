package song.spring4.domain.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupDto {

    @NotBlank(message = "username is required")
    @Size(min=10, message = "username must be at least 10 characters")
    private String username;
    private String password;
    private String name;
    private String email;
}
