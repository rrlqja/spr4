package song.spring4.exception;

public class FileEntityNotFoundException extends RuntimeException {
    public FileEntityNotFoundException() {
        super("File Entity Not Found Exception");
    }

    public FileEntityNotFoundException(String message) {
        super(message);
    }
}
