package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class TokenAlreadyVerifiedException extends NotFoundException {
    public TokenAlreadyVerifiedException() {
        super("Token Already Verified Exception");
    }

    public TokenAlreadyVerifiedException(String message) {
        super(message);
    }
}
