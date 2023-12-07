package song.spring4.exception.notfound;

public abstract class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Not Found Exception");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
