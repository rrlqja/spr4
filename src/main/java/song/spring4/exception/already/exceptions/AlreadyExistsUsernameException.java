package song.spring4.exception.already.exceptions;

import song.spring4.exception.already.AlreadyException;

public class AlreadyExistsUsernameException extends AlreadyException {
    public AlreadyExistsUsernameException() {
        super("Already Exists Username Exception");

    }

    public AlreadyExistsUsernameException(String message) {
        super(message);
    }
}
