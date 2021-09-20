package com.forbusypeople.budget.services.mails;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail() {
        var mailTo = "some@gmail.com";

        var simpleMail = new SimpleMailMessage();
        simpleMail.setTo(mailTo);
        simpleMail.setSubject("Test Mail");
        simpleMail.setText("Body in massage");

        javaMailSender.send(simpleMail);
    }

}
