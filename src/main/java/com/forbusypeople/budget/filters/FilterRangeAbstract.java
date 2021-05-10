package com.forbusypeople.budget.filters;

import com.forbusypeople.budget.enums.FilterExpensesParametersEnum;
import com.forbusypeople.budget.enums.MonthsEnum;
import com.forbusypeople.budget.repositories.entities.ExpensesEntity;
import com.forbusypeople.budget.repositories.entities.UserEntity;
import com.forbusypeople.budget.validators.ExpensesFilerParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public abstract class FilterRangeAbstract {

    @Autowired
    private ExpensesFilerParametersValidator parametersValidator;

    private final static String DATE_SUFFIX = "T00:00:00.001Z";

    public List<ExpensesEntity> getAllByFilter(UserEntity user,
                                               Map<String, String> filter) {
        parametersValidator.assertFilter(filter);

        if (isFilterForFromToDate(filter)) {
            var fromDate = filter.get(FilterExpensesParametersEnum.FROM_DATE.getKey());
            var toDate = filter.get(FilterExpensesParametersEnum.TO_DATE.getKey());

            return getAllEntityBetweenDate(user,
                                           parseDateToInstant(fromDate),
                                           parseDateToInstant(toDate)
            );
        } else if (isFilterForMonthYear(filter)) {
            MonthsEnum month = MonthsEnum.valueOf(
                    filter.get(FilterExpensesParametersEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterExpensesParametersEnum.YEAR.getKey());
            return getAllExpensesForMonthInYear(user, month, year);
        }

        return Collections.emptyList();
    }


    private boolean isFilterForMonthYear(Map<String, String> filter) {
        return filter.containsKey(FilterExpensesParametersEnum.YEAR.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.MONTH.getKey());
    }

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        return filter.containsKey(FilterExpensesParametersEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.TO_DATE.getKey());
    }

    private List<ExpensesEntity> getAllExpensesForMonthInYear(UserEntity user,
                                                              MonthsEnum month,
                                                              String year) {
        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllEntityBetweenDate(user, parseDateToInstant(from), parseDateToInstant(to));
    }

    private Instant parseDateToInstant(String date) {
        return Instant.parse(date + DATE_SUFFIX);
    }

    protected abstract List<ExpensesEntity> getAllEntityBetweenDate(UserEntity user,
                                                                    Instant fromDate,
                                                                    Instant toDate);
}
