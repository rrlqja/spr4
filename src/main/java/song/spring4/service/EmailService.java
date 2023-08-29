package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.EmailDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    @Transactional
    public EmailDto sendSimpleMessage(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dkclasltmf@naver.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        log.info("[send email] to = {}, message = {}", message.getTo(), message.getText());
        emailSender.send(message);

        EmailDto emailDto = new EmailDto();
        emailDto.setFromEmail(message.getFrom());
        emailDto.setToEmail(message.getTo());
        emailDto.setSubject(message.getSubject());
        emailDto.setContent(message.getText());

        return emailDto;
    }

    @Transactional
    public EmailDto sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dkclasltmf@naver.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        log.info("[send email] to = {}, message = {}", message.getTo(), message.getText());
        emailSender.send(message);

        EmailDto emailDto = new EmailDto();
        emailDto.setFromEmail(message.getFrom());
        emailDto.setToEmail(message.getTo());
        emailDto.setSubject(message.getSubject());
        emailDto.setContent(message.getText());

        return emailDto;
    }
}
