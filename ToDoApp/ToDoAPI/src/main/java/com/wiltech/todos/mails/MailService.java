package com.wiltech.todos.mails;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.wiltech.todos.exceptions.DomainException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    public void sendEmail(final NotificationEmail notificationEmail) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("donoreply@wiltech.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            this.mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (final MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new DomainException("Exception occurred when sending mail to " + notificationEmail.getRecipient() + " " + e.getMessage());
        }
    }
}
