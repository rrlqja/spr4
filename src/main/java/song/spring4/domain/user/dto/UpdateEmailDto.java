package song.spring4.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateEmailDto {
    @NotBlank
    private String email;
}
