package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.mails.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @GetMapping
    public void sendMail() {
        mailSenderService.sendMail();
    }

}
