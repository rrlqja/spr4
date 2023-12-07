package song.spring4.exception.already.exceptions;

import song.spring4.exception.already.AlreadyException;

public class AlreadyExistsEmailException extends AlreadyException {
    public AlreadyExistsEmailException() {
        super("이미 인증된 이메일 입니다.");
    }

    public AlreadyExistsEmailException(String message) {
        super(message);
    }
}
