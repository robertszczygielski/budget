package com.forbusypeople.budget.scheduleds;

import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.repositories.UserRepository;
import com.forbusypeople.budget.services.auditors.ExpensesAuditorService;
import com.forbusypeople.budget.services.mails.MailSenderService;
import com.forbusypeople.budget.services.users.AdditionalUserDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class ScheduleMailService {

    private final AuditorScheduledService auditorScheduledService;
    private final ExpensesAuditorService expensesAuditorService;
    private final UserRepository userRepository;
    private final AdditionalUserDataService additionalUserDataService;
    private final MailSenderService mailSenderService;

    @Scheduled(cron = "1 59 23 29-31 * *")
    public void scheduleMail() {
        var ldt = LocalDateTime.now();
        var month = ldt.getMonth().name();
        var year = String.valueOf(ldt.getYear());

        var allUsers = userRepository.findAll();

        allUsers.forEach(
                user -> {
                    var auditForEstimate =
                            expensesAuditorService.getAuditForEstimate(
                                    user,
                                    MonthsEnum.valueOf(month.toUpperCase()),
                                    year
                            );

                    var shouldSendMail = auditorScheduledService.mailShouldBeSend(auditForEstimate);

                    if (shouldSendMail) {
                        var additionalData = additionalUserDataService.getAdditionalData(user);
                        mailSenderService.sendMail(additionalData.getEmail(), auditForEstimate);
                        log.info("mail send to {}", additionalData.getEmail());
                    }

                });

    }

}
