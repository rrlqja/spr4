package song.spring4.exception;

public class TokenAlreadyVerifiedException extends RuntimeException {
    public TokenAlreadyVerifiedException() {
        super("Token Already Verified Exception");
    }

    public TokenAlreadyVerifiedException(String message) {
        super(message);
    }
}
