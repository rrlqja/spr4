package song.spring4.exception.invalid;

public class InvalidException extends RuntimeException {
    public InvalidException() {
        super("Invalid Exception");
    }

    public InvalidException(String message) {
        super(message);
    }
}
