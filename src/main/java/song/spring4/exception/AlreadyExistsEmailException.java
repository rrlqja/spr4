package song.spring4.exception;

public class AlreadyExistsEmailException extends RuntimeException {
    public AlreadyExistsEmailException() {
        super("이미 인증된 이메일 입니다.");
    }

    public AlreadyExistsEmailException(String message) {
        super(message);
    }
}
