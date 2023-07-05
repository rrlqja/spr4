package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class EmailNotFoundException extends NotFoundException {
    public EmailNotFoundException() {
        super("Email Not Found Exception");
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
