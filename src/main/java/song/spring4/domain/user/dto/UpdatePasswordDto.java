package song.spring4.domain.user.dto;

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
