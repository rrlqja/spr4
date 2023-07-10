package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException() {
        super("Board Not Found Exception");
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
