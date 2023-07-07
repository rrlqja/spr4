package song.spring4.controlleradvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.spring4.exception.AlreadyExistsUsernameException;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.exception.NotFoundException;
import song.spring4.exception.TokenAlreadyVerifiedException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler({NotFoundException.class, TokenAlreadyVerifiedException.class,
    AlreadyExistsUsernameException.class, IllegalRequestArgumentException.class})
    public ResponseEntity<String> notFoundExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<String> mailExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("이메일 전송에 실패했습니다.");
    }
}
