package song.spring4.exception.invalid.exceptions;

import song.spring4.exception.invalid.InvalidException;

public class IllegalArgumentException extends InvalidException {
    public IllegalArgumentException() {
        super("Illegal Argument Exception");
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
