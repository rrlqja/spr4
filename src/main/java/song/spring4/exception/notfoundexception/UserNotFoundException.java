package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User Not Found Exception");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
