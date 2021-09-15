package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.auditors.ExpensesAuditorService;
import com.forbusypeople.budget.services.dtos.AuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auditor")
public class ExpensesAuditorController {

    private final ExpensesAuditorService expensesAuditorService;
    private final ExpensesEstimatePercentageService expensesEstimatePercentageService;

    @GetMapping("/expenses/month/{month}/year/{year}")
    public BigDecimal getExpensesAudit(
            @PathVariable("month") String month,
            @PathVariable("year") String year
    ) {
        return expensesAuditorService.getAudit(MonthsEnum.valueOf(month.toUpperCase()), year);
    }


    @GetMapping("/expenses/month/{month}/year/{year}/estimate")
    public Map<ExpensesCategory, AuditDto> getExpensesEstimateAudit(
            @PathVariable("month") String month,
            @PathVariable("year") String year
    ) {
        return expensesAuditorService.getAuditForEstimate(
                MonthsEnum.valueOf(month.toUpperCase()),
                year
        );
    }

    @PostMapping("/estimate")
    public void saveEstimate(@RequestBody Map<String, String> estimate) {
        var estimatesToSave = estimate.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        it -> ExpensesCategory.valueOf(it.getKey().toUpperCase()),
                        it -> new BigDecimal(it.getValue())
                ));

        expensesEstimatePercentageService.saveEstimation(estimatesToSave);
    }

}
