package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException() {
        super("File Not Found Exception");
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
