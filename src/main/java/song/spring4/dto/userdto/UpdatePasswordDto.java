package song.spring4.dto.userdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePasswordDto {
    @NotBlank
    private String originalPassword;
    @NotBlank
    private String newPassword;
}
