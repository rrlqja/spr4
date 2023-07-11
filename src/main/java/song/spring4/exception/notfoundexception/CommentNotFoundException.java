package song.spring4.exception.notfoundexception;

import song.spring4.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super("Comment Not Found Exception");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
