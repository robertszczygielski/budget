package com.forbusypeople.budget.services.auditors;

import com.forbusypeople.budget.enums.ExpensesCategory;
import com.forbusypeople.budget.enums.FilterParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.services.AssetsService;
import com.forbusypeople.budget.services.ExpensesEstimatePercentageService;
import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
class ExpensesAuditorCalculator {

    private final ExpensesEstimatePercentageService expensesEstimatePercentageService;
    private final AssetsService assetsService;
    private final ExpensesService expensesService;

    BigDecimal getPlanPercentAudit(ExpensesCategory expensesCategory,
                                   BigDecimal assets) {
        var percent = expensesEstimatePercentageService.getEstimation()
                .get(expensesCategory)
                .divide(new BigDecimal("100"));

        return assets.multiply(percent);
    }

    BigDecimal getPlanPercentAudit(UserEntity user,
                                   ExpensesCategory expensesCategory,
                                   BigDecimal assets) {
        var percent = expensesEstimatePercentageService.getEstimation(user)
                .get(expensesCategory)
                .divide(new BigDecimal("100"));

        return assets.multiply(percent);
    }

    BigDecimal getRealPercentAudit(ExpensesCategory expensesCategory,
                                   MonthsEnum month,
                                   String year) {
        var expenses = getExpensesInMonth(month, year);
        return expenses.stream()
                .filter(dto -> dto.getCategory().equals(expensesCategory))
                .map(dto -> dto.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal getRealPercentAudit(UserEntity user,
                                   ExpensesCategory expensesCategory,
                                   MonthsEnum month,
                                   String year) {
        var expenses = getExpensesInMonth(user, month, year);
        return expenses.stream()
                .filter(dto -> dto.getCategory().equals(expensesCategory))
                .map(dto -> dto.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    List<ExpensesDto> getExpensesInMonth(MonthsEnum month,
                                         String year) {
        var filters = getFilters(month, year);
        return expensesService.getFilteredExpenses(filters);
    }

    List<ExpensesDto> getExpensesInMonth(UserEntity user,
                                         MonthsEnum month,
                                         String year) {
        var filters = getFilters(month, year);
        return expensesService.getFilteredExpenses(user, filters);
    }

    List<AssetDto> getAssetsInMonth(MonthsEnum month,
                                    String year) {
        var filters = getFilters(month, year);
        return assetsService.getAssetsByFilter(filters);
    }

    List<AssetDto> getAssetsInMonth(UserEntity user,
                                    MonthsEnum month,
                                    String year) {
        var filters = getFilters(month, year);
        return assetsService.getAssetsByFilter(user, filters);
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
