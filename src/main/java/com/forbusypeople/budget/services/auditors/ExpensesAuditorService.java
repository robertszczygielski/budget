package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.FilterParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExpensesAuditorService {

    private final AssetsService assetsService;
    private final ExpensesService expensesService;
    private final ExpensesEstimatePercentageService expensesEstimatePercentageService;

    public BigDecimal getAudit(MonthsEnum month,
                               String year) {
        var assetsInMonth = getAssetsInMonth(month, year);
        var expensesInMonth = getExpensesInMonth(month, year);

        var assetsSum = assetsInMonth.stream()
                .map(AssetDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var expenseSum = expensesInMonth.stream()
                .map(ExpensesDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return assetsSum.subtract(expenseSum);
    }

    public BigDecimal getPercentAudit(ExpensesCategory expensesCategory,
                                      BigDecimal assets) {
        var percent = expensesEstimatePercentageService.getEstimation()
                .get(expensesCategory)
                .divide(new BigDecimal("100"));

        return assets.multiply(percent);
    }

    private List<ExpensesDto> getExpensesInMonth(MonthsEnum month,
                                                 String year) {
        var filters = getFilters(month, year);
        return expensesService.getFilteredExpenses(filters);
    }

    private List<AssetDto> getAssetsInMonth(MonthsEnum month,
                                            String year) {
        var filters = getFilters(month, year);
        return assetsService.getAssetsByFilter(filters);
    }

    private Map<String, String> getFilters(MonthsEnum month,
                                           String year) {
        var fromDate = month.getFirstDayForYear(year);
        var toDate = month.getLastDayForYear(year);

        return new HashMap<>() {{
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
        }};

    }
}
