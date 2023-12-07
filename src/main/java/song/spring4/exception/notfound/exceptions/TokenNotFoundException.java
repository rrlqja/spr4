package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException() {
        super("Token Not Found Exception");
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
