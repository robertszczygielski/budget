package com.forbusypeople.budget.services.mails;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.dtos.AuditDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(String mailTo,
                         Map<ExpensesCategory, AuditDto> auditForEstimate) {
        var simpleMail = new SimpleMailMessage();
        simpleMail.setTo(mailTo);
        simpleMail.setSubject("Przekroczono wydatki");
        simpleMail.setText(auditForEstimate.toString());

        javaMailSender.send(simpleMail);
    }

}
