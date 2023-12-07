package song.spring4.exception.already.exceptions;

import song.spring4.exception.already.AlreadyException;

public class AlreadyVerifiedException extends AlreadyException {
    public AlreadyVerifiedException() {
        super("Token Already Verified Exception");
    }

    public AlreadyVerifiedException(String message) {
        super(message);
    }
}
