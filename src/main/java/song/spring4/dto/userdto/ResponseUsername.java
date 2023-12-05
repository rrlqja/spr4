package song.spring4.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseUsername {
    private String username;

    public ResponseUsername(String username) {
        this.username = username;
    }
}
