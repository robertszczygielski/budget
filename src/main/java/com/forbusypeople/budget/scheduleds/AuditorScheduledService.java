package com.forbusypeople.budget.scheduleds;

import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.auditors.ExpensesAuditorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class AuditorScheduledService {

    private final ExpensesAuditorService expensesAuditorService;

    public boolean mailShouldBeSend() {
        var ldt = LocalDateTime.now();
        var month = ldt.getMonth().name();
        var year = String.valueOf(ldt.getYear());

        var auditForEstimate = expensesAuditorService.getAuditForEstimate(
                MonthsEnum.valueOf(month.toUpperCase()),
                year
        );

        var diff = auditForEstimate.entrySet().stream()
                .filter(estimate -> {
                    var dto = estimate.getValue();
                    return dto.getCurrentAmount().compareTo(dto.getExpectedAmount()) > 0;
                })
                .collect(Collectors.toMap(
                        estimate -> estimate.getKey(),
                        estimate -> estimate.getValue()
                ));

        return !diff.isEmpty();
    }
}
