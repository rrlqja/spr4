package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException() {
        super("Token Not Found Exception");
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
