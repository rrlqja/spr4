package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User Not Found Exception");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
