package com.forbusypeople.budget.scheduleds;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.services.dtos.AuditDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
class AuditorScheduledService {

    public boolean mailShouldBeSend(Map<ExpensesCategory, AuditDto> auditForEstimate) {
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
