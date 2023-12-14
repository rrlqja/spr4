package song.spring4.exception.invalid.exceptions;

import song.spring4.exception.invalid.InvalidException;

public class InvalidUserException extends InvalidException {
    public InvalidUserException() {
        super("Invalid User Exception");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
