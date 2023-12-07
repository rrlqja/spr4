package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class FileEntityNotFoundException extends NotFoundException {
    public FileEntityNotFoundException() {
        super("File Entity Not Found Exception");
    }

    public FileEntityNotFoundException(String message) {
        super(message);
    }
}
