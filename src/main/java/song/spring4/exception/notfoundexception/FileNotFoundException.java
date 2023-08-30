package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException() {
        super("File Not Found Exception");
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
