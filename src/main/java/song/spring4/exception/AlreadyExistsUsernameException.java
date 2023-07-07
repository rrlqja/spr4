package song.spring4.exception;

public class AlreadyExistsUsernameException extends RuntimeException {
    public AlreadyExistsUsernameException() {
        super("Already Exists Username Exception");

    }

    public AlreadyExistsUsernameException(String message) {
        super(message);
    }
}
