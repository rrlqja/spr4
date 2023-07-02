package song.spring4.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token Not Found Exception");
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
