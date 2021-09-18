package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.AuditDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpensesAuditorService {

    private final ExpensesEstimatePercentageService expensesEstimatePercentageService;
    private final ExpensesAuditorCalculator expensesAuditorCalculator;

    public BigDecimal getAudit(MonthsEnum month,
                               String year) {
        var assetsInMonth = expensesAuditorCalculator.getAssetsInMonth(month, year);
        var expensesInMonth = expensesAuditorCalculator.getExpensesInMonth(month, year);

        var assetsSum = assetsInMonth.stream()
                .map(AssetDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var expenseSum = expensesInMonth.stream()
                .map(ExpensesDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return assetsSum.subtract(expenseSum);
    }

    public Map<ExpensesCategory, AuditDto> getAuditForEstimate(MonthsEnum monthsEnum,
                                                               String year) {
        var assetsInMonth = expensesAuditorCalculator.getAssetsInMonth(monthsEnum, year);
        var assetSum = assetsInMonth.stream()
                .map(AssetDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Arrays.stream(ExpensesCategory.values())
                .collect(Collectors.toMap(
                        category -> category,
                        category -> {
                            var planedExpenses =
                                    expensesAuditorCalculator.getPlanPercentAudit(category, assetSum);
                            var realExpenses =
                                    expensesAuditorCalculator.getRealPercentAudit(category, monthsEnum, year);

                            return AuditDto.builder()
                                    .currentAmount(realExpenses)
                                    .expectedAmount(planedExpenses)
                                    .percent(expensesEstimatePercentageService.getEstimation().get(category))
                                    .build();
                        }
                ));
    }

}
