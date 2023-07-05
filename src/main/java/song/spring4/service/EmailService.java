package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.EmailDto;
import song.spring4.entity.Email;
import song.spring4.exception.notfoundexception.EmailNotFoundException;
import song.spring4.repository.EmailJpaRepository;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailJpaRepository emailRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public Long saveEmail(EmailDto emailDto) {
        Email email = new Email();
        email.setFromEmail(emailDto.getFromEmail());
        email.setToEmail(Arrays.asList(emailDto.getToEmail()));
        email.setSubject(emailDto.getSubject());
        email.setContent(emailDto.getContent());

        Email saveEmail = emailRepository.save(email);
        return saveEmail.getId();
    }

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

    @Transactional
    public Email findEmailById(Long id) {
        Email findEmail = getById(id);

        return findEmail;
    }

    private Email getById(Long id) {
        return emailRepository.findById(id).orElseThrow(() ->
                new EmailNotFoundException("찾을 수 없습니다."));
    }
}
