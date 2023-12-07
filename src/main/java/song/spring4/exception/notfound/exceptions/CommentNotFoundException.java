package song.spring4.exception.notfound.exceptions;

import song.spring4.exception.notfound.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super("Comment Not Found Exception");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
