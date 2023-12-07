package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException() {
        super("Board Not Found Exception");
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
