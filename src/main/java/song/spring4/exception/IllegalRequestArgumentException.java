package song.spring4.exception;

public class IllegalRequestArgumentException extends RuntimeException {
    public IllegalRequestArgumentException() {
        super("Illegal Request Argument Exception");
    }

    public IllegalRequestArgumentException(String message) {
        super(message);
    }
}
