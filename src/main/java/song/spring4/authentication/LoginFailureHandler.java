package song.spring4.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Login Fail\nUsername = {}, Password = {}, Exception = {}",
                request.getParameter("username"), request.getParameter("password"), exception.getMessage());

        String errorMessage = "알 수 없는 오류가 발생하였습니다.";
        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "만료된 계정입니다.";
        } else if (exception instanceof LockedException) {
            errorMessage = "잠긴 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "인증 정보가 만료된 계정입니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "비활성화된 계정입니다.";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.write(errorMessage);
        writer.flush();
    }
}
